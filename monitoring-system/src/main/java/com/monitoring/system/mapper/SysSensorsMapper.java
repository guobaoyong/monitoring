package com.monitoring.system.mapper;

import java.util.List;
import com.monitoring.system.domain.SysSensors;

/**
 * 传感器管理Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
public interface SysSensorsMapper 
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
     * 删除传感器管理
     * 
     * @param sensorsId 传感器管理主键
     * @return 结果
     */
    public int deleteSysSensorsBySensorsId(Long sensorsId);

    /**
     * 批量删除传感器管理
     * 
     * @param sensorsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysSensorsBySensorsIds(String[] sensorsIds);
}
