package com.monitoring.system.mapper;

import java.util.List;
import com.monitoring.system.domain.SysCo;

/**
 * 覆盖优化记录Mapper接口
 * 

 * @date 2023-03-15
 */
public interface SysCoMapper 
{
    /**
     * 查询覆盖优化记录
     * 
     * @param coId 覆盖优化记录主键
     * @return 覆盖优化记录
     */
    public SysCo selectSysCoByCoId(Long coId);

    /**
     * 查询覆盖优化记录列表
     * 
     * @param sysCo 覆盖优化记录
     * @return 覆盖优化记录集合
     */
    public List<SysCo> selectSysCoList(SysCo sysCo);

    /**
     * 新增覆盖优化记录
     * 
     * @param sysCo 覆盖优化记录
     * @return 结果
     */
    public int insertSysCo(SysCo sysCo);

    /**
     * 修改覆盖优化记录
     * 
     * @param sysCo 覆盖优化记录
     * @return 结果
     */
    public int updateSysCo(SysCo sysCo);

    /**
     * 删除覆盖优化记录
     * 
     * @param coId 覆盖优化记录主键
     * @return 结果
     */
    public int deleteSysCoByCoId(Long coId);

    /**
     * 批量删除覆盖优化记录
     * 
     * @param coIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCoByCoIds(String[] coIds);
}
