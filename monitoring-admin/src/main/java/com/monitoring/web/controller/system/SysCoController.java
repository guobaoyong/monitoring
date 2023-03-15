package com.monitoring.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.monitoring.system.domain.SysDataRecord;
import com.monitoring.system.domain.SysNodeInfo;
import com.monitoring.system.service.ISysDataRecordService;
import com.monitoring.system.service.ISysNodeInfoService;
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
import com.monitoring.system.domain.SysCo;
import com.monitoring.system.service.ISysCoService;
import com.monitoring.common.core.controller.BaseController;
import com.monitoring.common.core.domain.AjaxResult;
import com.monitoring.common.utils.poi.ExcelUtil;
import com.monitoring.common.core.page.TableDataInfo;

/**
 * 覆盖优化记录Controller
 *
 * @author ruoyi
 * @date 2023-03-15
 */
@Controller
@RequestMapping("/system/co")
public class SysCoController extends BaseController {

    private String prefix = "system/co";

    @Autowired
    private ISysCoService sysCoService;

    @Autowired
    private ISysDataRecordService sysDataRecordService;

    @Autowired
    private ISysNodeInfoService sysNodeInfoService;

    @RequiresPermissions("system:co:view")
    @GetMapping()
    public String co() {
        return prefix + "/co";
    }

    /**
     * 查询覆盖优化记录列表
     */
    @RequiresPermissions("system:co:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysCo sysCo) {
        startPage();
        List<SysCo> list = sysCoService.selectSysCoList(sysCo);
        return getDataTable(list);
    }

    /**
     * 导出覆盖优化记录列表
     */
    @RequiresPermissions("system:co:export")
    @Log(title = "覆盖优化记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysCo sysCo) {
        List<SysCo> list = sysCoService.selectSysCoList(sysCo);
        ExcelUtil<SysCo> util = new ExcelUtil<SysCo>(SysCo.class);
        return util.exportExcel(list, "覆盖优化记录数据");
    }

    /**
     * 新增覆盖优化记录
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 新增保存覆盖优化记录
     */
    @RequiresPermissions("system:co:add")
    @Log(title = "覆盖优化记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysCo sysCo) {
        return toAjax(sysCoService.insertSysCo(sysCo));
    }

    /**
     * 修改覆盖优化记录
     */
    @RequiresPermissions("system:co:edit")
    @GetMapping("/edit/{coId}")
    public String edit(@PathVariable("coId") Long coId, ModelMap mmap) {
        SysCo sysCo = sysCoService.selectSysCoByCoId(coId);
        mmap.put("sysCo", sysCo);
        return prefix + "/edit";
    }

    /**
     * 修改保存覆盖优化记录
     */
    @RequiresPermissions("system:co:edit")
    @Log(title = "覆盖优化记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysCo sysCo) {
        return toAjax(sysCoService.updateSysCo(sysCo));
    }

    /**
     * 删除覆盖优化记录
     */
    @RequiresPermissions("system:co:remove")
    @Log(title = "覆盖优化记录", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(sysCoService.deleteSysCoByCoIds(ids));
    }

    /**
     * 开始覆盖优化
     */
    @RequiresPermissions("system:co:data")
    @Log(title = "覆盖优化记录", businessType = BusinessType.UPDATE)
    @PostMapping("/data")
    @ResponseBody
    public AjaxResult data(SysCo sysCo) {
        return toAjax(sysCoService.data(sysCo));
    }

    /**
     * 查看详细实验数据
     */
    @RequiresPermissions("system:co:figure")
    @GetMapping("/figure/{coId}")
    public String figure(SysCo sysCo, ModelMap mmap) {
        sysCo = sysCoService.selectSysCoByCoId(sysCo.getCoId());
        SysDataRecord sysDataRecord = sysDataRecordService.selectSysDataRecordByDataId(sysCo.getDataId());
        mmap.put("sysCo", sysCo);
        if (sysDataRecord.getDataDim().equals("0")) {
            return prefix + "/figure2D";
        } else {
            return prefix + "/figure3D";
        }
    }

    /**
     * 获取初始位置数据
     */
    @RequiresPermissions("system:co:figure")
    @PostMapping("/initLocationData")
    @ResponseBody
    public AjaxResult initLocationData(SysCo sysCo) {
        SysDataRecord sysDataRecord = sysDataRecordService.selectSysDataRecordByDataId(sysCo.getDataId());
        // 寻找位置记录
        SysNodeInfo sysNodeInfo = new SysNodeInfo();
        sysNodeInfo.setDataId(sysCo.getDataId());
        sysNodeInfo.setNodeType("0");
        List<SysNodeInfo> nodeInfoList = sysNodeInfoService.selectSysNodeInfoList(sysNodeInfo);
        List<Object> list = new ArrayList<>();
        nodeInfoList.forEach(nodeInfo -> {
            List<Double> temp = new ArrayList<>();
            temp.add(nodeInfo.getNodeX());
            temp.add(nodeInfo.getNodeY());
            if (sysDataRecord.getDataDim().equals("1")) {
                temp.add(nodeInfo.getNodeZ());
                temp.add(nodeInfo.getNodeRs());
            }
            list.add(temp);
        });
        return AjaxResult.success(list);
    }

    /**
     * 获取优化位置数据
     */
    @RequiresPermissions("system:co:figure")
    @PostMapping("/afterLocationData")
    @ResponseBody
    public AjaxResult afterLocationData(SysCo sysCo) {
        SysDataRecord sysDataRecord = sysDataRecordService.selectSysDataRecordByDataId(sysCo.getDataId());
        // 寻找位置记录
        SysNodeInfo sysNodeInfo = new SysNodeInfo();
        sysNodeInfo.setDataId(sysCo.getDataId());
        sysNodeInfo.setNodeType("1");
        List<SysNodeInfo> nodeInfoList = sysNodeInfoService.selectSysNodeInfoList(sysNodeInfo);
        List<Object> list = new ArrayList<>();
        nodeInfoList.forEach(nodeInfo -> {
            List<Double> temp = new ArrayList<>();
            temp.add(nodeInfo.getNodeX());
            temp.add(nodeInfo.getNodeY());
            if (sysDataRecord.getDataDim().equals("1")) {
                temp.add(nodeInfo.getNodeZ());
                temp.add(nodeInfo.getNodeRs());
            }
            list.add(temp);
        });
        return AjaxResult.success(list);
    }

    /**
     * 获取覆盖率数据
     */
    @RequiresPermissions("system:co:figure")
    @PostMapping("/coverageData")
    @ResponseBody
    public AjaxResult coverageData(SysCo sysCo) {
        sysCo = sysCoService.selectSysCoByCoId(sysCo.getCoId());
        String curveCoverage = sysCo.getCurveCoverage();
        JSONArray jsonArray = JSONObject.parseArray(curveCoverage);
        List<Object> list = new ArrayList<>();
        int index = 1;
        for (int i = 0; i < jsonArray.size(); i++) {
            List<Object> temp = new ArrayList<>();
            temp.add(index++);
            temp.add(jsonArray.get(i));
            list.add(temp);
        }
        return AjaxResult.success(list);
    }

    /**
     * 获取能量数据
     */
    @RequiresPermissions("system:co:figure")
    @PostMapping("/energyData")
    @ResponseBody
    public AjaxResult energyData(SysCo sysCo) {
        sysCo = sysCoService.selectSysCoByCoId(sysCo.getCoId());
        String curveEnergy = sysCo.getCurveEnergy();
        JSONArray jsonArray = JSONObject.parseArray(curveEnergy);
        List<Object> list = new ArrayList<>();
        int index = 1;
        for (int i = 0; i < jsonArray.size(); i++) {
            List<Object> temp = new ArrayList<>();
            temp.add(index++);
            temp.add(jsonArray.get(i));
            list.add(temp);
        }
        return AjaxResult.success(list);
    }

    /**
     * 查看详细实验数据
     */
    @RequiresPermissions("system:co:topology")
    @GetMapping("/topology")
    public String topology(ModelMap mmap) {
        List<SysDataRecord> sysDataRecords = sysDataRecordService.selectSysDataRecordList(null);
        mmap.put("sysDataRecords", sysDataRecords);
        List<Object> sysDataRecordsIds = new ArrayList<>();
        List<Object> sysDataRecordsDims = new ArrayList<>();
        sysDataRecords.forEach(sysDataRecord -> {
            sysDataRecordsIds.add(sysDataRecord.getDataId());
            sysDataRecordsDims.add(sysDataRecord.getDataDim());
        });
        mmap.put("sysDataRecordsIds", sysDataRecordsIds);
        mmap.put("sysDataRecordsDims", sysDataRecordsDims);
        return prefix + "/topology";
    }

    /**
     * 获取拓扑数据(节点)
     */
    @RequiresPermissions("system:co:topology")
    @PostMapping("/graphData")
    @ResponseBody
    public AjaxResult graphData(SysCo sysCo) {
        SysNodeInfo sysNodeInfo = new SysNodeInfo();
        sysNodeInfo.setDataId(sysCo.getDataId());
        sysNodeInfo.setNodeType("1");
        List<SysNodeInfo> sysNodeInfos = sysNodeInfoService.selectSysNodeInfoList(sysNodeInfo);
        List<Map<String, Object>> nodeList = new ArrayList<>();
        sysNodeInfos.forEach(nodeInfo -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", nodeInfo.getNodeSort());
            map.put("x", nodeInfo.getNodeX());
            map.put("y", nodeInfo.getNodeY());
            nodeList.add(map);
        });
        return AjaxResult.success(nodeList);
    }

    /**
     * 获取拓扑数据(节点)
     */
    @RequiresPermissions("system:co:topology")
    @PostMapping("/edgeData")
    @ResponseBody
    public AjaxResult edgeData(SysCo sysCo) {
        SysNodeInfo sysNodeInfo = new SysNodeInfo();
        sysNodeInfo.setDataId(sysCo.getDataId());
        sysNodeInfo.setNodeType("1");
        List<SysNodeInfo> sysNodeInfos = sysNodeInfoService.selectSysNodeInfoList(sysNodeInfo);
        List<Map<String, Object>> nodeList = new ArrayList<>();
        sysNodeInfos.forEach(nodeInfo -> {
            // 判断通信范围内的感知节点序号
            sysNodeInfos.forEach(node -> {
                if (!node.equals(nodeInfo)) {
                    double distance = Math.sqrt(Math.pow(nodeInfo.getNodeX() - node.getNodeX(), 2) +
                            Math.pow(nodeInfo.getNodeY() - node.getNodeY(), 2));
                    if (distance <= 2 * nodeInfo.getNodeRs()) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("source", nodeInfo.getNodeSort());
                        map.put("target", node.getNodeSort());
                        nodeList.add(map);
                    }
                }
            });
        });
        return AjaxResult.success(nodeList);
    }
}
