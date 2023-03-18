package com.monitoring.system.mapper;

import java.util.List;
import com.monitoring.system.domain.SysRoleDept;

/**
 * 角色与院系关联表 数据层
 * 

 */
public interface SysRoleDeptMapper
{
    /**
     * 通过角色ID删除角色和院系关联
     * 
     * @param roleId 角色ID
     * @return 结果
     */
    public int deleteRoleDeptByRoleId(Long roleId);

    /**
     * 批量删除角色院系关联信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRoleDept(Long[] ids);

    /**
     * 查询院系使用数量
     * 
     * @param deptId 院系ID
     * @return 结果
     */
    public int selectCountRoleDeptByDeptId(Long deptId);

    /**
     * 批量新增角色院系信息
     * 
     * @param roleDeptList 角色院系列表
     * @return 结果
     */
    public int batchRoleDept(List<SysRoleDept> roleDeptList);
}
