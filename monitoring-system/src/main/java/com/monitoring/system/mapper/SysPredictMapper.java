package com.monitoring.system.mapper;

import java.util.List;
import com.monitoring.system.domain.SysPredict;

/**
 * 预测记录Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-16
 */
public interface SysPredictMapper 
{
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
     * 删除预测记录
     * 
     * @param predictId 预测记录主键
     * @return 结果
     */
    public int deleteSysPredictByPredictId(Long predictId);

    /**
     * 批量删除预测记录
     * 
     * @param predictIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysPredictByPredictIds(String[] predictIds);
}
