package com.monitoring.system.service.impl;

import java.util.List;
import com.monitoring.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monitoring.system.mapper.SysSensorsMapper;
import com.monitoring.system.domain.SysSensors;
import com.monitoring.system.service.ISysSensorsService;
import com.monitoring.common.core.text.Convert;

/**
 * 传感器管理Service业务层处理
 * 

 * @date 2023-03-15
 */
@Service
public class SysSensorsServiceImpl implements ISysSensorsService 
{
    @Autowired
    private SysSensorsMapper sysSensorsMapper;

    /**
     * 查询传感器管理
     * 
     * @param sensorsId 传感器管理主键
     * @return 传感器管理
     */
    @Override
    public SysSensors selectSysSensorsBySensorsId(Long sensorsId)
    {
        return sysSensorsMapper.selectSysSensorsBySensorsId(sensorsId);
    }

    /**
     * 查询传感器管理列表
     * 
     * @param sysSensors 传感器管理
     * @return 传感器管理
     */
    @Override
    public List<SysSensors> selectSysSensorsList(SysSensors sysSensors)
    {
        return sysSensorsMapper.selectSysSensorsList(sysSensors);
    }

    /**
     * 新增传感器管理
     * 
     * @param sysSensors 传感器管理
     * @return 结果
     */
    @Override
    public int insertSysSensors(SysSensors sysSensors)
    {
        sysSensors.setCreateTime(DateUtils.getNowDate());
        return sysSensorsMapper.insertSysSensors(sysSensors);
    }

    /**
     * 修改传感器管理
     * 
     * @param sysSensors 传感器管理
     * @return 结果
     */
    @Override
    public int updateSysSensors(SysSensors sysSensors)
    {
        sysSensors.setUpdateTime(DateUtils.getNowDate());
        return sysSensorsMapper.updateSysSensors(sysSensors);
    }

    /**
     * 批量删除传感器管理
     * 
     * @param sensorsIds 需要删除的传感器管理主键
     * @return 结果
     */
    @Override
    public int deleteSysSensorsBySensorsIds(String sensorsIds)
    {
        return sysSensorsMapper.deleteSysSensorsBySensorsIds(Convert.toStrArray(sensorsIds));
    }

    /**
     * 删除传感器管理信息
     * 
     * @param sensorsId 传感器管理主键
     * @return 结果
     */
    @Override
    public int deleteSysSensorsBySensorsId(Long sensorsId)
    {
        return sysSensorsMapper.deleteSysSensorsBySensorsId(sensorsId);
    }
}
