package com.monitoring.web.controller.system;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.monitoring.system.domain.SysCo;
import com.monitoring.system.domain.SysNodeInfo;
import com.monitoring.system.domain.SysPredictDetail;
import com.monitoring.system.service.ISysPredictDetailService;
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
import com.monitoring.system.domain.SysPredict;
import com.monitoring.system.service.ISysPredictService;
import com.monitoring.common.core.controller.BaseController;
import com.monitoring.common.core.domain.AjaxResult;
import com.monitoring.common.utils.poi.ExcelUtil;
import com.monitoring.common.core.page.TableDataInfo;
import sun.misc.BASE64Encoder;

import javax.sql.rowset.serial.SerialBlob;


/**
 * 预测记录Controller
 *
 * @date 2023-03-16
 */
@Controller
@RequestMapping("/system/predict")
public class SysPredictController extends BaseController {

    private String prefix = "system/predict";

    @Autowired
    private ISysPredictService sysPredictService;

    @Autowired
    private ISysPredictDetailService sysPredictDetailService;

    @RequiresPermissions("system:predict:view")
    @GetMapping()
    public String predict() {
        return prefix + "/predict";
    }

    /**
     * 查询预测记录列表
     */
    @RequiresPermissions("system:predict:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysPredict sysPredict) {
        startPage();
        List<SysPredict> list = sysPredictService.selectSysPredictList(sysPredict);
        return getDataTable(list);
    }

    /**
     * 导出预测记录列表
     */
    @RequiresPermissions("system:predict:export")
    @Log(title = "预测记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysPredict sysPredict) {
        List<SysPredict> list = sysPredictService.selectSysPredictList(sysPredict);
        ExcelUtil<SysPredict> util = new ExcelUtil<SysPredict>(SysPredict.class);
        return util.exportExcel(list, "预测记录数据");
    }

    /**
     * 新增预测记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存预测记录
     */
    @RequiresPermissions("system:predict:add")
    @Log(title = "预测记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysPredict sysPredict) {
        return toAjax(sysPredictService.insertSysPredict(sysPredict));
    }

    /**
     * 修改预测记录
     */
    @RequiresPermissions("system:predict:edit")
    @GetMapping("/edit/{predictId}")
    public String edit(@PathVariable("predictId") Long predictId, ModelMap mmap) {
        SysPredict sysPredict = sysPredictService.selectSysPredictByPredictId(predictId);
        mmap.put("sysPredict", sysPredict);
        return prefix + "/edit";
    }

    /**
     * 修改保存预测记录
     */
    @RequiresPermissions("system:predict:edit")
    @Log(title = "预测记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysPredict sysPredict) {
        return toAjax(sysPredictService.updateSysPredict(sysPredict));
    }

    /**
     * 删除预测记录
     */
    @RequiresPermissions("system:predict:remove")
    @Log(title = "预测记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysPredictService.deleteSysPredictByPredictIds(ids));
    }

    /**
     * 预测记录图像
     */
    @RequiresPermissions("system:predict:figure")
    @GetMapping("/figure/{predictId}")
    public String figure(SysPredict sysPredict, ModelMap mmap) throws UnsupportedEncodingException, SQLException {
        sysPredict = sysPredictService.selectSysPredictByPredictId(sysPredict.getPredictId());
        mmap.put("sysPredict", sysPredict);
        return prefix + "/figure";
    }

    /**
     * 查看详细预测数据
     */
    @RequiresPermissions("system:predict:data")
    @GetMapping("/data/{predictId}")
    public String data(SysPredict sysPredict, ModelMap mmap) {
        sysPredict = sysPredictService.selectSysPredictByPredictId(sysPredict.getPredictId());
        mmap.put("sysPredict", sysPredict);
        return prefix + "/detail";
    }

    /**
     * 获取预测数据
     */
    @RequiresPermissions("system:predict:figure")
    @PostMapping("/predictIdData")
    @ResponseBody
    public AjaxResult predictIdData(SysPredict sysPredict, String type) {
        // 根据预测ID和预测日期拿到明日的数据
        SysPredictDetail sysPredictDetail = new SysPredictDetail();
        sysPredictDetail.setPredictId(sysPredict.getPredictId());
        List<SysPredictDetail> sysPredictDetails = sysPredictDetailService.selectSysPredictDetailList(sysPredictDetail);
        List<Object> list = new ArrayList<>();
        sysPredictDetails.forEach(predictDetail -> {
            if (type.equals("x")) {
                list.add(predictDetail.getPredictDay());
            } else if (type.equals("y")) {
                list.add(predictDetail.getPredictValue());
            }
        });
        return AjaxResult.success(list);
    }
}
