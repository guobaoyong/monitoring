package com.monitoring.web.controller.system;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.monitoring.common.annotation.Log;
import com.monitoring.common.enums.BusinessType;
import com.monitoring.system.domain.SysSensors;
import com.monitoring.system.service.ISysSensorsService;
import com.monitoring.common.core.controller.BaseController;
import com.monitoring.common.core.domain.AjaxResult;
import com.monitoring.common.utils.poi.ExcelUtil;
import com.monitoring.common.core.page.TableDataInfo;

/**
 * 传感器管理Controller
 * 

 * @date 2023-03-15
 */
@Controller
@RequestMapping("/system/sensors")
public class SysSensorsController extends BaseController
{
    private String prefix = "system/sensors";

    @Autowired
    private ISysSensorsService sysSensorsService;

    @RequiresPermissions("system:sensors:view")
    @GetMapping()
    public String sensors()
    {
        return prefix + "/sensors";
    }

    /**
     * 查询传感器管理列表
     */
    @RequiresPermissions("system:sensors:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysSensors sysSensors)
    {
        startPage();
        List<SysSensors> list = sysSensorsService.selectSysSensorsList(sysSensors);
        return getDataTable(list);
    }

    /**
     * 导出传感器管理列表
     */
    @RequiresPermissions("system:sensors:export")
    @Log(title = "传感器管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysSensors sysSensors)
    {
        List<SysSensors> list = sysSensorsService.selectSysSensorsList(sysSensors);
        ExcelUtil<SysSensors> util = new ExcelUtil<SysSensors>(SysSensors.class);
        return util.exportExcel(list, "传感器管理数据");
    }

    /**
     * 新增传感器管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存传感器管理
     */
    @RequiresPermissions("system:sensors:add")
    @Log(title = "传感器管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysSensors sysSensors)
    {
        return toAjax(sysSensorsService.insertSysSensors(sysSensors));
    }

    /**
     * 修改传感器管理
     */
    @RequiresPermissions("system:sensors:edit")
    @GetMapping("/edit/{sensorsId}")
    public String edit(@PathVariable("sensorsId") Long sensorsId, ModelMap mmap)
    {
        SysSensors sysSensors = sysSensorsService.selectSysSensorsBySensorsId(sensorsId);
        mmap.put("sysSensors", sysSensors);
        return prefix + "/edit";
    }

    /**
     * 修改保存传感器管理
     */
    @RequiresPermissions("system:sensors:edit")
    @Log(title = "传感器管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysSensors sysSensors)
    {
        return toAjax(sysSensorsService.updateSysSensors(sysSensors));
    }

    /**
     * 删除传感器管理
     */
    @RequiresPermissions("system:sensors:remove")
    @Log(title = "传感器管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysSensorsService.deleteSysSensorsBySensorsIds(ids));
    }
}
