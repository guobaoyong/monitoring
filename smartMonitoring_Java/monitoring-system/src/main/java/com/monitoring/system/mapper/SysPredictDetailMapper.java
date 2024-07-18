package com.monitoring.system.mapper;

import java.util.List;
import com.monitoring.system.domain.SysPredictDetail;

/**
 * 预测详情Mapper接口
 * 

 * @date 2023-03-17
 */
public interface SysPredictDetailMapper 
{
    /**
     * 查询预测详情
     * 
     * @param detailId 预测详情主键
     * @return 预测详情
     */
    public SysPredictDetail selectSysPredictDetailByDetailId(Long detailId);

    /**
     * 查询预测详情列表
     * 
     * @param sysPredictDetail 预测详情
     * @return 预测详情集合
     */
    public List<SysPredictDetail> selectSysPredictDetailList(SysPredictDetail sysPredictDetail);

    /**
     * 新增预测详情
     * 
     * @param sysPredictDetail 预测详情
     * @return 结果
     */
    public int insertSysPredictDetail(SysPredictDetail sysPredictDetail);

    /**
     * 修改预测详情
     * 
     * @param sysPredictDetail 预测详情
     * @return 结果
     */
    public int updateSysPredictDetail(SysPredictDetail sysPredictDetail);

    /**
     * 删除预测详情
     * 
     * @param detailId 预测详情主键
     * @return 结果
     */
    public int deleteSysPredictDetailByDetailId(Long detailId);

    /**
     * 批量删除预测详情
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysPredictDetailByDetailIds(String[] detailIds);
}
