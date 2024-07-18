package com.monitoring.web.controller.system;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.monitoring.common.core.controller.BaseController;
import com.monitoring.common.core.domain.AjaxResult;
import com.monitoring.framework.web.domain.Server;
import com.monitoring.system.domain.SysCollectData;
import com.monitoring.system.domain.SysNotice;
import com.monitoring.system.domain.SysSensors;
import com.monitoring.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据大屏
 */
@Controller
@RequestMapping("/system/dataScreen")
public class SysDataScreenController extends BaseController {

    @Autowired
    private ISysCollectDataService sysCollectDataService;

    @Autowired
    private ISysSensorsService sysSensorsService;

    @Autowired
    private ISysCoService sysCoService;

    @Autowired
    private ISysPredictService sysPredictService;

    @Autowired
    private ISysNoticeService sysNoticeService;

    @GetMapping()
    public String dataScreen(ModelMap mmap) {
        mmap.put("collectDataCount", String.valueOf(sysCollectDataService.selectCount()).toCharArray());
        SysSensors sensors = new SysSensors();
        sensors.setType("temperature");
        mmap.put("temperatureCount", sysSensorsService.selectSysSensorsList(sensors).size());
        sensors.setType("humidity");
        mmap.put("humidityCount", sysSensorsService.selectSysSensorsList(sensors).size());
        sensors.setType("light");
        mmap.put("lightCount", sysSensorsService.selectSysSensorsList(sensors).size());
        mmap.put("sensorsCount", sysSensorsService.selectCount());
        mmap.put("coCount", sysCoService.selectCount());
        SysCollectData collectData = new SysCollectData();
        collectData.setCollectTime(DateTime.now());
        mmap.put("todayCollectCount", sysCollectDataService.selectSysCollectDataList(collectData).size());
        mmap.put("predictCount", sysPredictService.selectCount());
        SysNotice notice = new SysNotice();
        notice.setWarningDate(DateUtil.today());
        mmap.put("todayNoticeCount", sysNoticeService.selectNoticeListCount(notice));
        Server server = new Server();
        try {
            server.copyTo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        DateTime parse = DateUtil.parse(server.getJvm().getRunTime());
        mmap.put("runTime", parse.getDay()*24+parse.getHours()*60+parse.getMinutes());
        List<SysNotice> sysNotices = sysNoticeService.selectNoticeList(notice);
        sysNotices.forEach(sysNotice -> {
            sysNotice.setRemark("事件发生地址："+sysSensorsService.selectSysSensorsBySensorsId(sysNotice.getSensorsId()).getAddress());
        });
        mmap.put("todayNotice",sysNotices);
        sensors.setType(null);
        mmap.put("sensorsList",sysSensorsService.selectSysSensorsList(sensors));
        return "datascreen/index";
    }

    @GetMapping("/notice")
    @ResponseBody
    public AjaxResult notice(Long id) {
        SysNotice sysNotice = sysNoticeService.selectNoticeById(id);
        sysNotice.setRemark("事件发生地址："+sysSensorsService.selectSysSensorsBySensorsId(sysNotice.getSensorsId()).getAddress());
        return AjaxResult.success(sysNotice);
    }

}

