package com.monitoring.system.service;

import java.util.List;
import com.monitoring.system.domain.SysSensors;

/**
 * 传感器管理Service接口
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
public interface ISysSensorsService 
{
    /**
     * 查询传感器管理
     * 
     * @param sensorsId 传感器管理主键
     * @return 传感器管理
     */
    public SysSensors selectSysSensorsBySensorsId(Long sensorsId);

    /**
     * 查询传感器管理列表
     * 
     * @param sysSensors 传感器管理
     * @return 传感器管理集合
     */
    public List<SysSensors> selectSysSensorsList(SysSensors sysSensors);

    /**
     * 新增传感器管理
     * 
     * @param sysSensors 传感器管理
     * @return 结果
     */
    public int insertSysSensors(SysSensors sysSensors);

    /**
     * 修改传感器管理
     * 
     * @param sysSensors 传感器管理
     * @return 结果
     */
    public int updateSysSensors(SysSensors sysSensors);

    /**
     * 批量删除传感器管理
     * 
     * @param sensorsIds 需要删除的传感器管理主键集合
     * @return 结果
     */
    public int deleteSysSensorsBySensorsIds(String sensorsIds);

    /**
     * 删除传感器管理信息
     * 
     * @param sensorsId 传感器管理主键
     * @return 结果
     */
    public int deleteSysSensorsBySensorsId(Long sensorsId);
}
