package com.monitoring.system.mapper;

import java.util.List;

import com.monitoring.system.domain.SysCollectData;

/**
 * 采集记录Mapper接口
 *
 * @date 2023-03-16
 */
public interface SysCollectDataMapper {

    /**
     * 查询采集记录
     *
     * @param collectId 采集记录主键
     * @return 采集记录
     */
    SysCollectData selectSysCollectDataByCollectId(Long collectId);

    /**
     * 查询采集记录列表
     *
     * @param sysCollectData 采集记录
     * @return 采集记录集合
     */
    List<SysCollectData> selectSysCollectDataList(SysCollectData sysCollectData);

    /**
     * 新增采集记录
     *
     * @param sysCollectData 采集记录
     * @return 结果
     */
    int insertSysCollectData(SysCollectData sysCollectData);

    /**
     * 修改采集记录
     *
     * @param sysCollectData 采集记录
     * @return 结果
     */
    int updateSysCollectData(SysCollectData sysCollectData);

    /**
     * 删除采集记录
     *
     * @param collectId 采集记录主键
     * @return 结果
     */
    int deleteSysCollectDataByCollectId(Long collectId);

    /**
     * 批量删除采集记录
     *
     * @param collectIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteSysCollectDataByCollectIds(String[] collectIds);

    /**
     * 查找 采集记录表 中 是否异常 和 是否预警 同时为空的数据
     *
     * @return 结果
     */
    List<SysCollectData> selectSysCollectDataListByBOthNull();

    /**
     * 查询传感器数量
     * @return 结果
     */
    int selectCount();
}
