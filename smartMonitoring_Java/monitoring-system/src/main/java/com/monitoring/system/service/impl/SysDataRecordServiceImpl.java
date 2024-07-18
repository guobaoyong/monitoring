package com.monitoring.system.service.impl;

import java.util.List;
import com.monitoring.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monitoring.system.mapper.SysDataRecordMapper;
import com.monitoring.system.domain.SysDataRecord;
import com.monitoring.system.service.ISysDataRecordService;
import com.monitoring.common.core.text.Convert;

/**
 * 位置数据Service业务层处理
 * 

 * @date 2023-03-15
 */
@Service
public class SysDataRecordServiceImpl implements ISysDataRecordService 
{
    @Autowired
    private SysDataRecordMapper sysDataRecordMapper;

    /**
     * 查询位置数据
     * 
     * @param dataId 位置数据主键
     * @return 位置数据
     */
    @Override
    public SysDataRecord selectSysDataRecordByDataId(Long dataId)
    {
        return sysDataRecordMapper.selectSysDataRecordByDataId(dataId);
    }

    /**
     * 查询位置数据列表
     * 
     * @param sysDataRecord 位置数据
     * @return 位置数据
     */
    @Override
    public List<SysDataRecord> selectSysDataRecordList(SysDataRecord sysDataRecord)
    {
        return sysDataRecordMapper.selectSysDataRecordList(sysDataRecord);
    }

    /**
     * 新增位置数据
     * 
     * @param sysDataRecord 位置数据
     * @return 结果
     */
    @Override
    public int insertSysDataRecord(SysDataRecord sysDataRecord)
    {
        sysDataRecord.setCreateTime(DateUtils.getNowDate());
        return sysDataRecordMapper.insertSysDataRecord(sysDataRecord);
    }

    /**
     * 修改位置数据
     * 
     * @param sysDataRecord 位置数据
     * @return 结果
     */
    @Override
    public int updateSysDataRecord(SysDataRecord sysDataRecord)
    {
        sysDataRecord.setUpdateTime(DateUtils.getNowDate());
        return sysDataRecordMapper.updateSysDataRecord(sysDataRecord);
    }

    /**
     * 批量删除位置数据
     * 
     * @param dataIds 需要删除的位置数据主键
     * @return 结果
     */
    @Override
    public int deleteSysDataRecordByDataIds(String dataIds)
    {
        return sysDataRecordMapper.deleteSysDataRecordByDataIds(Convert.toStrArray(dataIds));
    }

    /**
     * 删除位置数据信息
     * 
     * @param dataId 位置数据主键
     * @return 结果
     */
    @Override
    public int deleteSysDataRecordByDataId(Long dataId)
    {
        return sysDataRecordMapper.deleteSysDataRecordByDataId(dataId);
    }
}
