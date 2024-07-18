package com.monitoring.system.service;

import java.util.List;
import com.monitoring.system.domain.SysCo;

/**
 * 覆盖优化记录Service接口
 * 

 * @date 2023-03-15
 */
public interface ISysCoService 
{
    /**
     * 查询覆盖优化记录
     * 
     * @param coId 覆盖优化记录主键
     * @return 覆盖优化记录
     */
    SysCo selectSysCoByCoId(Long coId);

    /**
     * 查询覆盖优化记录列表
     * 
     * @param sysCo 覆盖优化记录
     * @return 覆盖优化记录集合
     */
    List<SysCo> selectSysCoList(SysCo sysCo);

    /**
     * 新增覆盖优化记录
     * 
     * @param sysCo 覆盖优化记录
     * @return 结果
     */
    int insertSysCo(SysCo sysCo);

    /**
     * 修改覆盖优化记录
     * 
     * @param sysCo 覆盖优化记录
     * @return 结果
     */
    int updateSysCo(SysCo sysCo);

    /**
     * 批量删除覆盖优化记录
     * 
     * @param coIds 需要删除的覆盖优化记录主键集合
     * @return 结果
     */
    int deleteSysCoByCoIds(String coIds);

    /**
     * 删除覆盖优化记录信息
     * 
     * @param coId 覆盖优化记录主键
     * @return 结果
     */
    int deleteSysCoByCoId(Long coId);

    /**
     * 开始覆盖优化
     * @param sysCo
     * @return
     */
    int data(SysCo sysCo);

    /**
     * 查询覆盖优化记录数量
     * @return 结果
     */
    int selectCount();
}
