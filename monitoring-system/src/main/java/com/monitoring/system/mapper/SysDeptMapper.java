package com.monitoring.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.monitoring.common.core.domain.entity.SysDept;

/**
 * 院系管理 数据层
 * 

 */
public interface SysDeptMapper
{
    /**
     * 查询下级院系数量
     * 
     * @param dept 院系信息
     * @return 结果
     */
    public int selectDeptCount(SysDept dept);

    /**
     * 查询院系是否存在用户
     * 
     * @param deptId 院系ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 查询院系管理数据
     * 
     * @param dept 院系信息
     * @return 院系信息集合
     */
    public List<SysDept> selectDeptList(SysDept dept);

    /**
     * 删除院系管理信息
     * 
     * @param deptId 院系ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    /**
     * 新增院系信息
     * 
     * @param dept 院系信息
     * @return 结果
     */
    public int insertDept(SysDept dept);

    /**
     * 修改院系信息
     * 
     * @param dept 院系信息
     * @return 结果
     */
    public int updateDept(SysDept dept);

    /**
     * 修改子元素关系
     * 
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 根据院系ID查询信息
     * 
     * @param deptId 院系ID
     * @return 院系信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 校验院系名称是否唯一
     * 
     * @param deptName 院系名称
     * @param parentId 父院系ID
     * @return 结果
     */
    public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

    /**
     * 根据角色ID查询院系
     *
     * @param roleId 角色ID
     * @return 院系列表
     */
    public List<String> selectRoleDeptTree(Long roleId);

    /**
     * 修改所在院系正常状态
     * 
     * @param deptIds 院系ID组
     */
    public void updateDeptStatusNormal(Long[] deptIds);

    /**
     * 根据ID查询所有子院系
     * 
     * @param deptId 院系ID
     * @return 院系列表
     */
    public List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 根据ID查询所有子院系（正常状态）
     * 
     * @param deptId 院系ID
     * @return 子院系数
     */
    public int selectNormalChildrenDeptById(Long deptId);
}
