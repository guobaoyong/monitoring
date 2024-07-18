package com.monitoring.quartz.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.monitoring.framework.web.service.ConfigService;
import com.monitoring.system.domain.*;
import com.monitoring.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component("predictTask")
@Service
public class PredictTask {

    @Autowired
    private ISysSensorsService sysSensorsService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    @Autowired
    private ISysPredictService sysPredictService;

    @Autowired
    private ISysCollectDataService sysCollectDataService;

    @Autowired
    private ISysPredictDetailService predictDetailService;

    public void predict() {
        List<SysSensors> sysSensors = sysSensorsService.selectSysSensorsList(null);
        String address = configService.getKey("sys.predict.address");
        sysSensors.forEach(sensors -> {
            DateTime start = DateUtil.date();
            // 远程执行数据
            String flag = "正常";
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("sensors_id", sensors.getSensorsId());
            paramMap.put("type", sensors.getType());
            String type = "";
            switch (sensors.getType()) {
                case "temperature":
                    type = "温度";
                    break;
                case "humidity":
                    type = "湿度";
                    break;
                case "light":
                    type = "光照";
                    break;
            }
            String result = HttpRequest.get(address)
                    .header(Header.USER_AGENT, "Hutool http")
                    .form(paramMap)//表单内容
                    .timeout(1000000)//超时，毫秒
                    .execute().body();
            JSONObject jsonObject = JSONObject.parseObject(result);
            try {
                if (!jsonObject.get("code").equals(200)) {
                    flag = "异常";
                }
            } catch (Exception exception) {
                flag = "异常";
            }
            DateTime end = DateUtil.date();
            // 生成执行记录
            SysNotice notice = new SysNotice();
            AtomicReference<String> record = new AtomicReference<>(StrUtil.format("ID为{}的{}传感器，于{}开始执行数据预测任务，{}结束，耗时：{}秒，执行状态：{}", sensors.getSensorsId(), type, start, end, DateUtil.between(start, end, DateUnit.SECOND), flag));
            notice.setNoticeTitle("预测执行过程记录");
            notice.setCreateBy("admin");
            // 执行记录
            notice.setNoticeType("1");
            notice.setStatus(flag.equals("正常") ? "0" : "1");
            notice.setNoticeContent(record.get());
            notice.setWarningDate(DateUtil.formatDateTime(end));
            notice.setSensorsId(sensors.getSensorsId());
            sysNoticeService.insertNotice(notice);
            // 生成详细预测数据
            String tomorrow = DateUtil.tomorrow().toDateStr();
            SysPredict sysPredict = new SysPredict();
            sysPredict.setPredictDay(tomorrow);
            sysPredict.setSensorId(sensors.getSensorsId());
            List<SysPredict> sysPredicts = sysPredictService.selectSysPredictList(sysPredict);
            String finalType = type;
            sysPredicts.forEach(predict -> {
                // 获取预测数据
                JSONArray jsonArray = JSONArray.parseArray(predict.getPredictCurve());
                // 查找训练数据
                String[] splitId = predict.getPredictTest().split(",");
                Calendar calendar = Calendar.getInstance();
                for (int i = 0; i < splitId.length; i++) {
                    SysCollectData sysCollectData = sysCollectDataService.selectSysCollectDataByCollectId(Long.valueOf(splitId[i]));
                    Date collectTime = sysCollectData.getCollectTime();
                    calendar.setTime(collectTime);
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);
                    String newDate = DateUtil.tomorrow().toDateStr() + " " + (String.valueOf(hour).length() == 1 ? "0" + hour : hour)
                            + ":" + (String.valueOf(minute).length() == 1 ? "0" + minute : minute)
                            + ":" + (String.valueOf(second).length() == 1 ? "0" + second : second);
                    // 查重
                    SysPredictDetail sysPredictDetail = new SysPredictDetail();
                    sysPredictDetail.setPredictId(predict.getPredictId());
                    sysPredictDetail.setPredictDay(newDate);
                    if (predictDetailService.selectSysPredictDetailList(sysPredictDetail).size() == 0) {
                        // 准备写入预测详情
                        try {
                            sysPredictDetail.setPredictValue(((JSONArray) jsonArray.get(i)).getString(0));
                        } catch (Exception exception) {
                            sysPredictDetail.setPredictValue("");
                        }
                        predictDetailService.insertSysPredictDetail(sysPredictDetail);
                        // 是否需要事前预警
                        try {
                            if (Double.valueOf(sysPredictDetail.getPredictValue()) >= Double.valueOf(sensors.getEarlyWarning())) {
                                // 写入事前预警信息
                                record.set(StrUtil.format("ID为{}的{}传感器，经预测，将于{}达到{}，可能超过预警值，请及时关注！", sensors.getSensorsId(), finalType, sysPredictDetail.getPredictDay(), sysPredictDetail.getPredictValue()));
                                notice.setNoticeTitle("事前预警记录");
                                notice.setCreateBy("admin");
                                notice.setNoticeType("2");
                                notice.setStatus("1");
                                notice.setNoticeContent(record.get());
                                notice.setWarningDate(sysPredictDetail.getPredictDay());
                                notice.setSensorsId(sensors.getSensorsId());
                                sysNoticeService.insertNotice(notice);
                            }
                        } catch (Exception exception) {
                            // 遇到无法比对的数据，进行跳过
                        }
                    }
                }
            });
        });
    }
}
