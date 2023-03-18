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
import com.monitoring.system.domain.SysPredictDetail;
import com.monitoring.system.service.ISysPredictDetailService;
import com.monitoring.common.core.controller.BaseController;
import com.monitoring.common.core.domain.AjaxResult;
import com.monitoring.common.utils.poi.ExcelUtil;
import com.monitoring.common.core.page.TableDataInfo;

/**
 * 预测详情Controller
 * 

 * @date 2023-03-17
 */
@Controller
@RequestMapping("/system/detail")
public class SysPredictDetailController extends BaseController
{
    private String prefix = "system/detail";

    @Autowired
    private ISysPredictDetailService sysPredictDetailService;

    @RequiresPermissions("system:detail:view")
    @GetMapping()
    public String detail()
    {
        return prefix + "/detail";
    }

    /**
     * 查询预测详情列表
     */
    @RequiresPermissions("system:detail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPredictDetail sysPredictDetail)
    {
        startPage();
        List<SysPredictDetail> list = sysPredictDetailService.selectSysPredictDetailList(sysPredictDetail);
        return getDataTable(list);
    }

    /**
     * 导出预测详情列表
     */
    @RequiresPermissions("system:detail:export")
    @Log(title = "预测详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysPredictDetail sysPredictDetail)
    {
        List<SysPredictDetail> list = sysPredictDetailService.selectSysPredictDetailList(sysPredictDetail);
        ExcelUtil<SysPredictDetail> util = new ExcelUtil<SysPredictDetail>(SysPredictDetail.class);
        return util.exportExcel(list, "预测详情数据");
    }

    /**
     * 新增预测详情
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存预测详情
     */
    @RequiresPermissions("system:detail:add")
    @Log(title = "预测详情", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysPredictDetail sysPredictDetail)
    {
        return toAjax(sysPredictDetailService.insertSysPredictDetail(sysPredictDetail));
    }

    /**
     * 修改预测详情
     */
    @RequiresPermissions("system:detail:edit")
    @GetMapping("/edit/{detailId}")
    public String edit(@PathVariable("detailId") Long detailId, ModelMap mmap)
    {
        SysPredictDetail sysPredictDetail = sysPredictDetailService.selectSysPredictDetailByDetailId(detailId);
        mmap.put("sysPredictDetail", sysPredictDetail);
        return prefix + "/edit";
    }

    /**
     * 修改保存预测详情
     */
    @RequiresPermissions("system:detail:edit")
    @Log(title = "预测详情", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysPredictDetail sysPredictDetail)
    {
        return toAjax(sysPredictDetailService.updateSysPredictDetail(sysPredictDetail));
    }

    /**
     * 删除预测详情
     */
    @RequiresPermissions("system:detail:remove")
    @Log(title = "预测详情", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(sysPredictDetailService.deleteSysPredictDetailByDetailIds(ids));
    }
}
