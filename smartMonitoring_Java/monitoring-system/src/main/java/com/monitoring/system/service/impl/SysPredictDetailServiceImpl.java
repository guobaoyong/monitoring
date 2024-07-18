package com.monitoring.system.service.impl;

import java.util.List;
import com.monitoring.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monitoring.system.mapper.SysPredictDetailMapper;
import com.monitoring.system.domain.SysPredictDetail;
import com.monitoring.system.service.ISysPredictDetailService;
import com.monitoring.common.core.text.Convert;

/**
 * 预测详情Service业务层处理
 * 

 * @date 2023-03-17
 */
@Service
public class SysPredictDetailServiceImpl implements ISysPredictDetailService 
{
    @Autowired
    private SysPredictDetailMapper sysPredictDetailMapper;

    /**
     * 查询预测详情
     * 
     * @param detailId 预测详情主键
     * @return 预测详情
     */
    @Override
    public SysPredictDetail selectSysPredictDetailByDetailId(Long detailId)
    {
        return sysPredictDetailMapper.selectSysPredictDetailByDetailId(detailId);
    }

    /**
     * 查询预测详情列表
     * 
     * @param sysPredictDetail 预测详情
     * @return 预测详情
     */
    @Override
    public List<SysPredictDetail> selectSysPredictDetailList(SysPredictDetail sysPredictDetail)
    {
        return sysPredictDetailMapper.selectSysPredictDetailList(sysPredictDetail);
    }

    /**
     * 新增预测详情
     * 
     * @param sysPredictDetail 预测详情
     * @return 结果
     */
    @Override
    public int insertSysPredictDetail(SysPredictDetail sysPredictDetail)
    {
        sysPredictDetail.setCreateTime(DateUtils.getNowDate());
        return sysPredictDetailMapper.insertSysPredictDetail(sysPredictDetail);
    }

    /**
     * 修改预测详情
     * 
     * @param sysPredictDetail 预测详情
     * @return 结果
     */
    @Override
    public int updateSysPredictDetail(SysPredictDetail sysPredictDetail)
    {
        sysPredictDetail.setUpdateTime(DateUtils.getNowDate());
        return sysPredictDetailMapper.updateSysPredictDetail(sysPredictDetail);
    }

    /**
     * 批量删除预测详情
     * 
     * @param detailIds 需要删除的预测详情主键
     * @return 结果
     */
    @Override
    public int deleteSysPredictDetailByDetailIds(String detailIds)
    {
        return sysPredictDetailMapper.deleteSysPredictDetailByDetailIds(Convert.toStrArray(detailIds));
    }

    /**
     * 删除预测详情信息
     * 
     * @param detailId 预测详情主键
     * @return 结果
     */
    @Override
    public int deleteSysPredictDetailByDetailId(Long detailId)
    {
        return sysPredictDetailMapper.deleteSysPredictDetailByDetailId(detailId);
    }
}
