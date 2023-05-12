package com.monitoring.quartz.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.monitoring.system.domain.SysCollectData;
import com.monitoring.system.domain.SysNotice;
import com.monitoring.system.domain.SysSensors;
import com.monitoring.system.service.ISysCollectDataService;
import com.monitoring.system.service.ISysNoticeService;
import com.monitoring.system.service.ISysSensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component("afterWarningTask")
@Service
public class AfterWarningTask {

    @Autowired
    private ISysCollectDataService sysCollectDataService;

    @Autowired
    private ISysSensorsService sysSensorsService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    public void warning() {
        // 查找 采集记录表 中 是否异常 和 是否预警 同时为空的数据，进行处理
        List<SysCollectData> sysCollectData = sysCollectDataService.selectSysCollectDataListByBOthNull();
        sysCollectData.forEach(collectData -> {
            SysSensors sysSensors = sysSensorsService.selectSysSensorsBySensorsId(collectData.getSensorId());
            Double dataValue = 0.0;
            String type = "";
            if (sysSensors.getType().equals("temperature")) {
                dataValue = Double.valueOf(collectData.getTemperature());
                type = "温度";
            } else if (sysSensors.getType().equals("humidity")) {
                dataValue = Double.valueOf(collectData.getHumidity());
                type = "湿度";
            } else if (sysSensors.getType().equals("light")) {
                dataValue = Double.valueOf(collectData.getLight());
                type = "光照";
            }
            if (dataValue >= Double.valueOf(sysSensors.getEarlyWarning())) {
                collectData.setAbnormal("0");
                collectData.setEarlyWarning("0");
                // 插入预警记录表
                SysNotice notice = new SysNotice();
                notice.setNoticeTitle("事后预警记录");
                notice.setNoticeContent(StrUtil.format("ID为{}的{}传感器，已于{}达到{}，超过预警值，请及时关注！", sysSensors.getSensorsId(), type, DateUtil.formatDateTime(collectData.getCollectTime()), dataValue));
                notice.setCreateBy("admin");
                notice.setNoticeType("3");
                notice.setStatus("1");
                sysNoticeService.insertNotice(notice);
            } else {
                collectData.setAbnormal("1");
                collectData.setEarlyWarning("1");
            }
            // 更新采集记录
            sysCollectDataService.updateSysCollectData(collectData);
        });
    }

}
