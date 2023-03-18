package com.monitoring.system.service;

import java.util.List;
import com.monitoring.system.domain.SysDataRecord;

/**
 * 位置数据Service接口
 * 

 * @date 2023-03-15
 */
public interface ISysDataRecordService 
{
    /**
     * 查询位置数据
     * 
     * @param dataId 位置数据主键
     * @return 位置数据
     */
    public SysDataRecord selectSysDataRecordByDataId(Long dataId);

    /**
     * 查询位置数据列表
     * 
     * @param sysDataRecord 位置数据
     * @return 位置数据集合
     */
    public List<SysDataRecord> selectSysDataRecordList(SysDataRecord sysDataRecord);

    /**
     * 新增位置数据
     * 
     * @param sysDataRecord 位置数据
     * @return 结果
     */
    public int insertSysDataRecord(SysDataRecord sysDataRecord);

    /**
     * 修改位置数据
     * 
     * @param sysDataRecord 位置数据
     * @return 结果
     */
    public int updateSysDataRecord(SysDataRecord sysDataRecord);

    /**
     * 批量删除位置数据
     * 
     * @param dataIds 需要删除的位置数据主键集合
     * @return 结果
     */
    public int deleteSysDataRecordByDataIds(String dataIds);

    /**
     * 删除位置数据信息
     * 
     * @param dataId 位置数据主键
     * @return 结果
     */
    public int deleteSysDataRecordByDataId(Long dataId);
}
