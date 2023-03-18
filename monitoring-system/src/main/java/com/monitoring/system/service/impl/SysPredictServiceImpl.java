package com.monitoring.system.service.impl;

import java.util.List;
import com.monitoring.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monitoring.system.mapper.SysPredictMapper;
import com.monitoring.system.domain.SysPredict;
import com.monitoring.system.service.ISysPredictService;
import com.monitoring.common.core.text.Convert;

/**
 * 预测记录Service业务层处理
 * 

 * @date 2023-03-16
 */
@Service
public class SysPredictServiceImpl implements ISysPredictService 
{
    @Autowired
    private SysPredictMapper sysPredictMapper;

    /**
     * 查询预测记录
     * 
     * @param predictId 预测记录主键
     * @return 预测记录
     */
    @Override
    public SysPredict selectSysPredictByPredictId(Long predictId)
    {
        return sysPredictMapper.selectSysPredictByPredictId(predictId);
    }

    /**
     * 查询预测记录列表
     * 
     * @param sysPredict 预测记录
     * @return 预测记录
     */
    @Override
    public List<SysPredict> selectSysPredictList(SysPredict sysPredict)
    {
        return sysPredictMapper.selectSysPredictList(sysPredict);
    }

    /**
     * 新增预测记录
     * 
     * @param sysPredict 预测记录
     * @return 结果
     */
    @Override
    public int insertSysPredict(SysPredict sysPredict)
    {
        sysPredict.setCreateTime(DateUtils.getNowDate());
        return sysPredictMapper.insertSysPredict(sysPredict);
    }

    /**
     * 修改预测记录
     * 
     * @param sysPredict 预测记录
     * @return 结果
     */
    @Override
    public int updateSysPredict(SysPredict sysPredict)
    {
        sysPredict.setUpdateTime(DateUtils.getNowDate());
        return sysPredictMapper.updateSysPredict(sysPredict);
    }

    /**
     * 批量删除预测记录
     * 
     * @param predictIds 需要删除的预测记录主键
     * @return 结果
     */
    @Override
    public int deleteSysPredictByPredictIds(String predictIds)
    {
        return sysPredictMapper.deleteSysPredictByPredictIds(Convert.toStrArray(predictIds));
    }

    /**
     * 删除预测记录信息
     * 
     * @param predictId 预测记录主键
     * @return 结果
     */
    @Override
    public int deleteSysPredictByPredictId(Long predictId)
    {
        return sysPredictMapper.deleteSysPredictByPredictId(predictId);
    }
}
