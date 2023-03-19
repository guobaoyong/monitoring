package com.monitoring.system.service;

import java.util.List;

import com.monitoring.system.domain.SysPredict;

/**
 * 预测记录Service接口
 *
 * @date 2023-03-16
 */
public interface ISysPredictService {
    /**
     * 查询预测记录
     *
     * @param predictId 预测记录主键
     * @return 预测记录
     */
    public SysPredict selectSysPredictByPredictId(Long predictId);

    /**
     * 查询预测记录列表
     *
     * @param sysPredict 预测记录
     * @return 预测记录集合
     */
    public List<SysPredict> selectSysPredictList(SysPredict sysPredict);

    /**
     * 新增预测记录
     *
     * @param sysPredict 预测记录
     * @return 结果
     */
    public int insertSysPredict(SysPredict sysPredict);

    /**
     * 修改预测记录
     *
     * @param sysPredict 预测记录
     * @return 结果
     */
    public int updateSysPredict(SysPredict sysPredict);

    /**
     * 批量删除预测记录
     *
     * @param predictIds 需要删除的预测记录主键集合
     * @return 结果
     */
    public int deleteSysPredictByPredictIds(String predictIds);

    /**
     * 删除预测记录信息
     *
     * @param predictId 预测记录主键
     * @return 结果
     */
    public int deleteSysPredictByPredictId(Long predictId);

    /**
     * 查询传感器数量
     *
     * @return 结果
     */
    int selectCount();
}
