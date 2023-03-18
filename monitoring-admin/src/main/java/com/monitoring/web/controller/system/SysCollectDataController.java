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
import com.monitoring.system.domain.SysCollectData;
import com.monitoring.system.service.ISysCollectDataService;
import com.monitoring.common.core.controller.BaseController;
import com.monitoring.common.core.domain.AjaxResult;
import com.monitoring.common.utils.poi.ExcelUtil;
import com.monitoring.common.core.page.TableDataInfo;

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
}
