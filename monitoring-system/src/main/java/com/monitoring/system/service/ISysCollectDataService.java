package com.monitoring.system.service;

import java.util.List;
import com.monitoring.system.domain.SysCollectData;

/**
 * 采集记录Service接口
 * 
 * @author ruoyi
 * @date 2023-03-16
 */
public interface ISysCollectDataService 
{
    /**
     * 查询采集记录
     * 
     * @param collectId 采集记录主键
     * @return 采集记录
     */
    public SysCollectData selectSysCollectDataByCollectId(Long collectId);

    /**
     * 查询采集记录列表
     * 
     * @param sysCollectData 采集记录
     * @return 采集记录集合
     */
    public List<SysCollectData> selectSysCollectDataList(SysCollectData sysCollectData);

    /**
     * 新增采集记录
     * 
     * @param sysCollectData 采集记录
     * @return 结果
     */
    public int insertSysCollectData(SysCollectData sysCollectData);

    /**
     * 修改采集记录
     * 
     * @param sysCollectData 采集记录
     * @return 结果
     */
    public int updateSysCollectData(SysCollectData sysCollectData);

    /**
     * 批量删除采集记录
     * 
     * @param collectIds 需要删除的采集记录主键集合
     * @return 结果
     */
    public int deleteSysCollectDataByCollectIds(String collectIds);

    /**
     * 删除采集记录信息
     * 
     * @param collectId 采集记录主键
     * @return 结果
     */
    public int deleteSysCollectDataByCollectId(Long collectId);
}
