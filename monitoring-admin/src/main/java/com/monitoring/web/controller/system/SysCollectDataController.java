package com.monitoring.web.controller.system;

import cn.hutool.core.date.DateUtil;
import com.monitoring.common.annotation.Log;
import com.monitoring.common.core.controller.BaseController;
import com.monitoring.common.core.domain.AjaxResult;
import com.monitoring.common.core.page.TableDataInfo;
import com.monitoring.common.enums.BusinessType;
import com.monitoring.common.utils.poi.ExcelUtil;
import com.monitoring.system.domain.SysCollectData;
import com.monitoring.system.domain.SysSensors;
import com.monitoring.system.service.ISysCollectDataService;
import com.monitoring.system.service.ISysSensorsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 采集记录Controller
 *
 * @date 2023-03-16
 */
@Controller
@RequestMapping("/system/data")
public class SysCollectDataController extends BaseController {

    private String prefix = "system/data";

    @Autowired
    private ISysCollectDataService sysCollectDataService;

    @Autowired
    private ISysSensorsService sysSensorsService;

    @RequiresPermissions("system:data:view")
    @GetMapping()
    public String data() {
        return prefix + "/data";
    }

    /**
     * 查询采集记录列表
     */
    @RequiresPermissions("system:data:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysCollectData sysCollectData) {
        startPage();
        List<SysCollectData> list = sysCollectDataService.selectSysCollectDataList(sysCollectData);
        return getDataTable(list);
    }

    /**
     * 导出采集记录列表
     */
    @RequiresPermissions("system:data:export")
    @Log(title = "采集记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysCollectData sysCollectData) {
        List<SysCollectData> list = sysCollectDataService.selectSysCollectDataList(sysCollectData);
        ExcelUtil<SysCollectData> util = new ExcelUtil<SysCollectData>(SysCollectData.class);
        return util.exportExcel(list, "采集记录数据");
    }

    /**
     * 新增采集记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存采集记录
     */
    @RequiresPermissions("system:data:add")
    @Log(title = "采集记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysCollectData sysCollectData) {
        return toAjax(sysCollectDataService.insertSysCollectData(sysCollectData));
    }

    /**
     * 修改采集记录
     */
    @RequiresPermissions("system:data:edit")
    @GetMapping("/edit/{collectId}")
    public String edit(@PathVariable("collectId") Long collectId, ModelMap mmap) {
        SysCollectData sysCollectData = sysCollectDataService.selectSysCollectDataByCollectId(collectId);
        mmap.put("sysCollectData", sysCollectData);
        return prefix + "/edit";
    }

    /**
     * 修改保存采集记录
     */
    @RequiresPermissions("system:data:edit")
    @Log(title = "采集记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysCollectData sysCollectData) {
        return toAjax(sysCollectDataService.updateSysCollectData(sysCollectData));
    }

    /**
     * 删除采集记录
     */
    @RequiresPermissions("system:data:remove")
    @Log(title = "采集记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysCollectDataService.deleteSysCollectDataByCollectIds(ids));
    }

    /**
     * 查看采集图像
     */
    @RequiresPermissions("system:data:figure")
    @GetMapping("/figure")
    public String figure(ModelMap mmap) {
        List<SysSensors> sysSensors = sysSensorsService.selectSysSensorsList(null);
        List<Object> sysSensorsIds = new ArrayList<>();
        sysSensors.forEach(sensor -> {
            if (sensor.getType().equals("temperature")) {
                sensor.setType("温度传感器");
            } else if (sensor.getType().equals("humidity")) {
                sensor.setType("湿度传感器");
            }
            sysSensorsIds.add(sensor.getSensorsId());
        });
        mmap.put("sysSensors", sysSensors);
        mmap.put("sysSensorsIds", sysSensorsIds);
        mmap.put("today", DateUtil.today());
        return prefix + "/figure";
    }

    /**
     * 获取采集数据
     */
    @RequiresPermissions("system:data:figure")
    @PostMapping("/collectData")
    @ResponseBody
    public AjaxResult collectData(SysCollectData collectData, String type) {
        SysSensors sensors = sysSensorsService.selectSysSensorsBySensorsId(collectData.getSensorId());
        collectData.setCollectTime(DateUtil.date());
        List<SysCollectData> sysCollectData = sysCollectDataService.selectSysCollectDataList(collectData);
        List<Object> list = new ArrayList<>();
        sysCollectData.forEach(collect -> {
            if (type.equals("x")) {
                list.add(collect.getCollectTime());
            } else if (type.equals("y")) {
                list.add(sensors.getType().equals("temperature") ? collect.getTemperature() : collect.getHumidity());
            }
        });
        return AjaxResult.success(list);
    }

    /**
     * 统计采集数据
     */
    @RequiresPermissions("system:data:figure")
    @PostMapping("/statisticsData")
    @ResponseBody
    public AjaxResult statisticsData(SysCollectData collectData) {
        SysSensors sensors = sysSensorsService.selectSysSensorsBySensorsId(collectData.getSensorId());
        collectData.setCollectTime(DateUtil.date());
        List<SysCollectData> sysCollectData = sysCollectDataService.selectSysCollectDataList(collectData);
        Double earlyWarning = Double.valueOf(sensors.getEarlyWarning());
        AtomicInteger normal = new AtomicInteger();
        AtomicInteger abnormal = new AtomicInteger();
        sysCollectData.forEach(collect -> {
            Double value = Double.valueOf(sensors.getType().equals("temperature") ? collect.getTemperature() : collect.getHumidity());
            if (value >= earlyWarning) {
                abnormal.getAndIncrement();
            } else {
                normal.getAndIncrement();
            }
        });
        HashMap<String,Integer> result = new HashMap<>();
        result.put("normal",normal.get());
        result.put("abnormal",abnormal.get());
        return AjaxResult.success(result);
    }
}
