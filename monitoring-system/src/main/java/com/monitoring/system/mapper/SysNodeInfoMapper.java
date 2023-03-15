package com.monitoring.system.mapper;

import java.util.List;
import com.monitoring.system.domain.SysNodeInfo;

/**
 * 位置详情Mapper接口
 * 
 * @author ruoyi
 * @date 2023-03-15
 */
public interface SysNodeInfoMapper 
{
    /**
     * 查询位置详情
     * 
     * @param nodeId 位置详情主键
     * @return 位置详情
     */
    public SysNodeInfo selectSysNodeInfoByNodeId(Long nodeId);

    /**
     * 查询位置详情列表
     * 
     * @param sysNodeInfo 位置详情
     * @return 位置详情集合
     */
    public List<SysNodeInfo> selectSysNodeInfoList(SysNodeInfo sysNodeInfo);

    /**
     * 新增位置详情
     * 
     * @param sysNodeInfo 位置详情
     * @return 结果
     */
    public int insertSysNodeInfo(SysNodeInfo sysNodeInfo);

    /**
     * 修改位置详情
     * 
     * @param sysNodeInfo 位置详情
     * @return 结果
     */
    public int updateSysNodeInfo(SysNodeInfo sysNodeInfo);

    /**
     * 删除位置详情
     * 
     * @param nodeId 位置详情主键
     * @return 结果
     */
    public int deleteSysNodeInfoByNodeId(Long nodeId);

    /**
     * 批量删除位置详情
     * 
     * @param nodeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysNodeInfoByNodeIds(String[] nodeIds);
}
