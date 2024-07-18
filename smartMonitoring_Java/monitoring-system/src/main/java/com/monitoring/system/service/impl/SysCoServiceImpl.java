package com.monitoring.system.service.impl;

import java.util.List;
import java.util.Random;

import GWOCO.GWOCO;
import GWOD.GWO3D;
import com.alibaba.fastjson.JSONArray;
import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;
import com.monitoring.common.utils.DateUtils;
import com.monitoring.system.domain.SysDataRecord;
import com.monitoring.system.domain.SysNodeInfo;
import com.monitoring.system.mapper.SysDataRecordMapper;
import com.monitoring.system.mapper.SysNodeInfoMapper;
import compute3DCover.Compute3DCover;
import computeCover.ComputeCoverage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monitoring.system.mapper.SysCoMapper;
import com.monitoring.system.domain.SysCo;
import com.monitoring.system.service.ISysCoService;
import com.monitoring.common.core.text.Convert;

/**
 * 覆盖优化记录Service业务层处理
 *

 * @date 2023-03-15
 */
@Service
public class SysCoServiceImpl implements ISysCoService {
    @Autowired
    private SysCoMapper sysCoMapper;

    @Autowired
    private SysNodeInfoMapper sysNodeInfoMapper;

    @Autowired
    private SysDataRecordMapper sysDataRecordMapper;

    /**
     * 查询覆盖优化记录
     *
     * @param coId 覆盖优化记录主键
     * @return 覆盖优化记录
     */
    @Override
    public SysCo selectSysCoByCoId(Long coId) {
        return sysCoMapper.selectSysCoByCoId(coId);
    }

    /**
     * 查询覆盖优化记录列表
     *
     * @param sysCo 覆盖优化记录
     * @return 覆盖优化记录
     */
    @Override
    public List<SysCo> selectSysCoList(SysCo sysCo) {
        return sysCoMapper.selectSysCoList(sysCo);
    }

    /**
     * 新增覆盖优化记录
     *
     * @param sysCo 覆盖优化记录
     * @return 结果
     */
    @Override
    public int insertSysCo(SysCo sysCo) {
        sysCo.setCreateTime(DateUtils.getNowDate());
        // 查重
        SysCo co = new SysCo();
        co.setDataId(sysCo.getDataId());
        if (sysCoMapper.selectSysCoList(co).size() == 0) {
            return sysCoMapper.insertSysCo(sysCo);
        } else {
            return 0;
        }
    }

    /**
     * 修改覆盖优化记录
     *
     * @param sysCo 覆盖优化记录
     * @return 结果
     */
    @Override
    public int updateSysCo(SysCo sysCo) {
        sysCo.setUpdateTime(DateUtils.getNowDate());
        return sysCoMapper.updateSysCo(sysCo);
    }

    /**
     * 批量删除覆盖优化记录
     *
     * @param coIds 需要删除的覆盖优化记录主键
     * @return 结果
     */
    @Override
    public int deleteSysCoByCoIds(String coIds) {
        return sysCoMapper.deleteSysCoByCoIds(Convert.toStrArray(coIds));
    }

    /**
     * 删除覆盖优化记录信息
     *
     * @param coId 覆盖优化记录主键
     * @return 结果
     */
    @Override
    public int deleteSysCoByCoId(Long coId) {
        return sysCoMapper.deleteSysCoByCoId(coId);
    }

    @Override
    public int data(SysCo sysCo) {
        double pop = 5.0;
        double inter = 300.0;
        long start = System.currentTimeMillis();
        // 从数据库中拿出记录
        SysCo coverageRecord = sysCoMapper.selectSysCoByCoId(sysCo.getCoId());
        SysDataRecord sysDataRecord = sysDataRecordMapper.selectSysDataRecordByDataId(coverageRecord.getDataId());
        // 计算初始覆盖率
        SysNodeInfo sysNodeInfo = new SysNodeInfo();
        sysNodeInfo.setDataId(sysDataRecord.getDataId());
        List<SysNodeInfo> nodeInfoList = sysNodeInfoMapper.selectSysNodeInfoList(sysNodeInfo);
        double[] x = new double[nodeInfoList.size()];
        double[] y = new double[nodeInfoList.size()];
        double[] z = new double[nodeInfoList.size()];
        for (int i = 0; i < nodeInfoList.size(); i++) {
            x[i] = nodeInfoList.get(i).getNodeX();
            y[i] = nodeInfoList.get(i).getNodeY();
            if (sysDataRecord.getDataDim().equals("1")) {
                z[i] = nodeInfoList.get(i).getNodeZ();
            }
        }
        Double L = Double.valueOf(sysDataRecord.getDataNum());
        Double nodeRs = nodeInfoList.get(0).getNodeRs();
        int index = 4;
        if (sysDataRecord.getDataDim().equals("0")) {
            try {
                ComputeCoverage t = new ComputeCoverage();
                Object[] result = t.computeCover(1, x, y, L, nodeRs, 0.4); //第一个参数是指定返回结果的个数；第二个参数起是传入matlab函数的参数，多个用逗号隔开。
                coverageRecord.setOrigin(Double.valueOf(result[0].toString()));
            } catch (MWException e) {
                e.printStackTrace();
            }
        } else if (sysDataRecord.getDataDim().equals("1")) {
            try {
                Compute3DCover t = new Compute3DCover();
                Object[] result = t.compute3DCover(1, x, y, z, L, nodeRs, 0.4); //第一个参数是指定返回结果的个数；第二个参数起是传入matlab函数的参数，多个用逗号隔开。
                coverageRecord.setOrigin(Double.valueOf(result[0].toString()));
                index = 5;
            } catch (MWException e) {
                e.printStackTrace();
            }
        }
        // 调用覆盖优化算法
        try {
            Object[] result = new Object[index];
            if (sysDataRecord.getDataDim().equals("0")) {
                GWOCO gwo = new GWOCO();
                result = gwo.GWO(index, pop, inter, Double.valueOf(nodeInfoList.size()), "WSNcover", nodeRs, L);//第一个参数是指定返回结果的个数；第二个参数起是传入matlab函数的参数，多个用逗号隔开。
            } else if (sysDataRecord.getDataDim().equals("1")) {
                GWO3D gwo3D = new GWO3D();
                result = gwo3D.GWOD(index, pop, inter, Double.valueOf(nodeInfoList.size()), "WSNcoverD", nodeRs, L);
            }
            // 优化后覆盖率
            coverageRecord.setAfterCoverage(Double.valueOf(result[0].toString()));
            // 优化后x的位置
            MWNumericArray mwNumericArrayx = (MWNumericArray) result[1];
            MWNumericArray mwNumericArrayy = (MWNumericArray) result[2];
            MWNumericArray mwNumericArrayz = null;
            if (sysDataRecord.getDataDim().equals("1")) {
                mwNumericArrayz = (MWNumericArray) result[3];
            }
            // 初始能量
            double lastEnergy = coverageRecord.getEnergy();
            for (int i = 1; i <= nodeInfoList.size(); i++) {
                SysNodeInfo sysNodeInfoTemp = nodeInfoList.get(i - 1);
                double newx = Double.valueOf(mwNumericArrayx.get(i).toString());
                double newy = Double.valueOf(mwNumericArrayy.get(i).toString());
                double distance = Math.sqrt(Math.pow(sysNodeInfoTemp.getNodeX() - newx, 2) + Math.pow(sysNodeInfoTemp.getNodeY() - newy, 2));
                double newz = 0.0;
                if (sysDataRecord.getDataDim().equals(1)) {
                    newz = Double.valueOf(mwNumericArrayz.get(i).toString());
                    distance = Math.sqrt(Math.pow(sysNodeInfoTemp.getNodeX() - newx, 2)
                            + Math.pow(sysNodeInfoTemp.getNodeY() - newy, 2)
                            + Math.pow(sysNodeInfoTemp.getNodeZ() - newz, 2));
                    sysNodeInfoTemp.setNodeZ(newz);
                }

                double nodeLastEnergy = distance * (sysNodeInfoTemp.getNodeEnergy() / 200);
                lastEnergy -= Math.abs(nodeLastEnergy);
                sysNodeInfoTemp.setNodeEnergy(sysNodeInfoTemp.getNodeEnergy() - nodeLastEnergy);
                sysNodeInfoTemp.setNodeX(newx);
                sysNodeInfoTemp.setNodeY(newy);
                // 优化后y的位置
                sysNodeInfoTemp.setNodeId(null);
                sysNodeInfoTemp.setNodeType(String.valueOf(1));
                // 更新节点信息
                sysNodeInfoMapper.insertSysNodeInfo(sysNodeInfoTemp);
            }
            // 网络剩余能量
            coverageRecord.setAfterEnergy(lastEnergy);
            // 覆盖曲线数据
            MWNumericArray mwNumericArrayCurve = (MWNumericArray) result[index - 1];
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < inter; i++) {
                jsonArray.add(mwNumericArrayCurve.get(i + 1));
            }
            coverageRecord.setCurveCoverage(jsonArray.toJSONString());
            // 能量消耗曲线
            double unit = (coverageRecord.getEnergy() - lastEnergy) / inter;
            JSONArray energyCurve = new JSONArray();
            energyCurve.add(coverageRecord.getEnergy());
            Random random = new Random(1);
            double tempEnergy = coverageRecord.getEnergy();
            for (int i = 0; i < inter - 2; i++) {
                double v = random.nextDouble() * 2.0 * unit;
                tempEnergy -= v;
                energyCurve.add(tempEnergy);
            }
            energyCurve.add(lastEnergy);
            coverageRecord.setCurveEnergy(energyCurve.toJSONString());
            // 判断网络状态，节点没有死亡
            String status = "正常";
            for (int i = 0; i < nodeInfoList.size(); i++) {
                if (nodeInfoList.get(i).getNodeEnergy() < 0) {
                    status = "异常";
                    break;
                }
            }
            coverageRecord.setStatus(status);
        } catch (MWException e) {
            e.printStackTrace();
        }
        long time = (System.currentTimeMillis() - start) / 1000;
        coverageRecord.setRunTime(String.valueOf(time));
        coverageRecord.setUpdateTime(DateUtils.getNowDate());
        return sysCoMapper.updateSysCo(coverageRecord);
    }

    @Override
    public int selectCount() {
        return sysCoMapper.selectCount();
    }
}
