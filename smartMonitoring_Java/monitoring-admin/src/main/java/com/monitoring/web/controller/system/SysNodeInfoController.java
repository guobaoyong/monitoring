package com.monitoring.web.controller.system;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.monitoring.system.domain.SysCo;
import com.monitoring.system.service.ISysCoService;
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
import com.monitoring.system.domain.SysNodeInfo;
import com.monitoring.system.service.ISysNodeInfoService;
import com.monitoring.common.core.controller.BaseController;
import com.monitoring.common.core.domain.AjaxResult;
import com.monitoring.common.utils.poi.ExcelUtil;
import com.monitoring.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 实验数据Controller
 *
 * @author Guo Baoyong NWNU
 * @date 2022-11-06
 */
@Controller
@RequestMapping("/system/info")
public class SysNodeInfoController extends BaseController {

    private String prefix = "system/info";

    @Autowired
    private ISysNodeInfoService sysNodeInfoService;

    @Autowired
    private ISysCoService sysCoService;

    @RequiresPermissions("system:info:view")
    @GetMapping()
    public String info() {
        return prefix + "/info";
    }

    /**
     * 查询实验数据列表
     */
    @RequiresPermissions("system:info:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysNodeInfo sysNodeInfo) {
        startPage();
        List<SysNodeInfo> list = sysNodeInfoService.selectSysNodeInfoList(sysNodeInfo);
        return getDataTable(list);
    }

    /**
     * 导出实验数据列表
     */
    @RequiresPermissions("system:info:export")
    @Log(title = "实验数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysNodeInfo sysNodeInfo) {
        List<SysNodeInfo> list = sysNodeInfoService.selectSysNodeInfoList(sysNodeInfo);
        ExcelUtil<SysNodeInfo> util = new ExcelUtil<SysNodeInfo>(SysNodeInfo.class);
        return util.exportExcel(list, "实验数据数据");
    }

    /**
     * 新增实验数据
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存实验数据
     */
    @RequiresPermissions("system:info:add")
    @Log(title = "实验数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysNodeInfo sysNodeInfo) {
        return toAjax(sysNodeInfoService.insertSysNodeInfo(sysNodeInfo));
    }

    /**
     * 修改实验数据
     */
    @RequiresPermissions("system:info:edit")
    @GetMapping("/edit/{nodeId}")
    public String edit(@PathVariable("nodeId") Long nodeId, ModelMap mmap) {
        SysNodeInfo sysNodeInfo = sysNodeInfoService.selectSysNodeInfoByNodeId(nodeId);
        mmap.put("sysNodeInfo", sysNodeInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存实验数据
     */
    @RequiresPermissions("system:info:edit")
    @Log(title = "实验数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysNodeInfo sysNodeInfo) {
        return toAjax(sysNodeInfoService.updateSysNodeInfo(sysNodeInfo));
    }

    /**
     * 删除实验数据
     */
    @RequiresPermissions("system:info:remove")
    @Log(title = "实验数据", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysNodeInfoService.deleteSysNodeInfoByNodeIds(ids));
    }

    @RequiresPermissions("system:info:importTemplate")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SysNodeInfo> util = new ExcelUtil<SysNodeInfo>(SysNodeInfo.class);
        return util.importTemplateExcel("实验数据");
    }

    @Log(title = "位置数据管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:info:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SysNodeInfo> util = new ExcelUtil<>(SysNodeInfo.class);
        List<SysNodeInfo> nodeInfoList = util.importExcel(file.getInputStream());
        String message = sysNodeInfoService.importSysNodeInfo(nodeInfoList);
        return AjaxResult.success(message);
    }

    /**
     * 提交覆盖优化任务
     */
    @RequiresPermissions("system:info:task")
    @Log(title = "实验数据管理", businessType = BusinessType.UPDATE)
    @PostMapping("/task")
    @ResponseBody
    public AjaxResult task(SysNodeInfo sysNodeInfo) {
        // 构造一个SysCo
        SysCo sysCo = new SysCo();
        List<SysNodeInfo> nodeInfoList = sysNodeInfoService.selectSysNodeInfoList(sysNodeInfo);
        AtomicReference<Double> energys = new AtomicReference<>((double) 0);
        nodeInfoList.forEach(nodeInfo -> {
            energys.updateAndGet(v -> new Double((double) (v + nodeInfo.getNodeEnergy())));
        });
        sysCo.setDataId(sysNodeInfo.getDataId());
        sysCo.setEnergy(energys.get());
        return toAjax(sysCoService.insertSysCo(sysCo));
    }

}
