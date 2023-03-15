package com.monitoring.web.controller.system;

import java.util.List;

import com.monitoring.system.domain.SysNodeInfo;
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
import com.monitoring.system.domain.SysDataRecord;
import com.monitoring.system.service.ISysDataRecordService;
import com.monitoring.common.core.controller.BaseController;
import com.monitoring.common.core.domain.AjaxResult;
import com.monitoring.common.utils.poi.ExcelUtil;
import com.monitoring.common.core.page.TableDataInfo;

/**
 * 位置数据Controller
 *
 * @author ruoyi
 * @date 2023-03-15
 */
@Controller
@RequestMapping("/system/record")
public class SysDataRecordController extends BaseController {
    private String prefix = "system/record";

    @Autowired
    private ISysDataRecordService sysDataRecordService;

    @RequiresPermissions("system:record:view")
    @GetMapping()
    public String record() {
        return prefix + "/record";
    }

    /**
     * 查询位置数据列表
     */
    @RequiresPermissions("system:record:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysDataRecord sysDataRecord) {
        startPage();
        List<SysDataRecord> list = sysDataRecordService.selectSysDataRecordList(sysDataRecord);
        return getDataTable(list);
    }

    /**
     * 导出位置数据列表
     */
    @RequiresPermissions("system:record:export")
    @Log(title = "位置数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDataRecord sysDataRecord) {
        List<SysDataRecord> list = sysDataRecordService.selectSysDataRecordList(sysDataRecord);
        ExcelUtil<SysDataRecord> util = new ExcelUtil<SysDataRecord>(SysDataRecord.class);
        return util.exportExcel(list, "位置数据数据");
    }

    /**
     * 新增位置数据
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存位置数据
     */
    @RequiresPermissions("system:record:add")
    @Log(title = "位置数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysDataRecord sysDataRecord) {
        return toAjax(sysDataRecordService.insertSysDataRecord(sysDataRecord));
    }

    /**
     * 修改位置数据
     */
    @RequiresPermissions("system:record:edit")
    @GetMapping("/edit/{dataId}")
    public String edit(@PathVariable("dataId") Long dataId, ModelMap mmap) {
        SysDataRecord sysDataRecord = sysDataRecordService.selectSysDataRecordByDataId(dataId);
        mmap.put("sysDataRecord", sysDataRecord);
        return prefix + "/edit";
    }

    /**
     * 修改保存位置数据
     */
    @RequiresPermissions("system:record:edit")
    @Log(title = "位置数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysDataRecord sysDataRecord) {
        return toAjax(sysDataRecordService.updateSysDataRecord(sysDataRecord));
    }

    /**
     * 删除位置数据
     */
    @RequiresPermissions("system:record:remove")
    @Log(title = "位置数据", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysDataRecordService.deleteSysDataRecordByDataIds(ids));
    }

    /**
     * 查看详细位置数据
     */
    @RequiresPermissions("system:record:data")
    @GetMapping("/data/{dataId}")
    public String data(SysNodeInfo sysNodeInfo, ModelMap mmap) {
        mmap.put("sysNodeInfo", sysNodeInfo);
        return prefix + "/info";
    }

    @RequiresPermissions("system:record:import")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SysNodeInfo> util = new ExcelUtil<>(SysNodeInfo.class);
        return util.importTemplateExcel("实验数据");
    }

}
