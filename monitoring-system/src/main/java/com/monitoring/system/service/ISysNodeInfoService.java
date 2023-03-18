package com.monitoring.system.service;

import java.util.List;

import com.monitoring.system.domain.SysNodeInfo;

/**
 * 位置详情Service接口
 *

 * @date 2023-03-15
 */
public interface ISysNodeInfoService {
    /**
     * 查询位置详情
     *
     * @param nodeId 位置详情主键
     * @return 位置详情
     */
    SysNodeInfo selectSysNodeInfoByNodeId(Long nodeId);

    /**
     * 查询位置详情列表
     *
     * @param sysNodeInfo 位置详情
     * @return 位置详情集合
     */
    List<SysNodeInfo> selectSysNodeInfoList(SysNodeInfo sysNodeInfo);

    /**
     * 新增位置详情
     *
     * @param sysNodeInfo 位置详情
     * @return 结果
     */
    int insertSysNodeInfo(SysNodeInfo sysNodeInfo);

    /**
     * 修改位置详情
     *
     * @param sysNodeInfo 位置详情
     * @return 结果
     */
    int updateSysNodeInfo(SysNodeInfo sysNodeInfo);

    /**
     * 批量删除位置详情
     *
     * @param nodeIds 需要删除的位置详情主键集合
     * @return 结果
     */
    int deleteSysNodeInfoByNodeIds(String nodeIds);

    /**
     * 删除位置详情信息
     *
     * @param nodeId 位置详情主键
     * @return 结果
     */
    int deleteSysNodeInfoByNodeId(Long nodeId);

    /**
     * 导入实验数据
     *
     * @param nodeInfoList 用户数据列表
     * @return 结果
     */
    String importSysNodeInfo(List<SysNodeInfo> nodeInfoList);
}
