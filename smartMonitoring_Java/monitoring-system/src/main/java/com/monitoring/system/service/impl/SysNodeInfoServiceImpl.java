package com.monitoring.system.service.impl;

import java.util.List;

import com.monitoring.common.exception.ServiceException;
import com.monitoring.common.utils.DateUtils;
import com.monitoring.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.monitoring.system.mapper.SysNodeInfoMapper;
import com.monitoring.system.domain.SysNodeInfo;
import com.monitoring.system.service.ISysNodeInfoService;
import com.monitoring.common.core.text.Convert;

/**
 * 位置详情Service业务层处理
 *

 * @date 2023-03-15
 */
@Service
public class SysNodeInfoServiceImpl implements ISysNodeInfoService {

    private static final Logger log = LoggerFactory.getLogger(SysNodeInfoServiceImpl.class);

    @Autowired
    private SysNodeInfoMapper sysNodeInfoMapper;

    /**
     * 查询位置详情
     *
     * @param nodeId 位置详情主键
     * @return 位置详情
     */
    @Override
    public SysNodeInfo selectSysNodeInfoByNodeId(Long nodeId) {
        return sysNodeInfoMapper.selectSysNodeInfoByNodeId(nodeId);
    }

    /**
     * 查询位置详情列表
     *
     * @param sysNodeInfo 位置详情
     * @return 位置详情
     */
    @Override
    public List<SysNodeInfo> selectSysNodeInfoList(SysNodeInfo sysNodeInfo) {
        return sysNodeInfoMapper.selectSysNodeInfoList(sysNodeInfo);
    }

    /**
     * 新增位置详情
     *
     * @param sysNodeInfo 位置详情
     * @return 结果
     */
    @Override
    public int insertSysNodeInfo(SysNodeInfo sysNodeInfo) {
        sysNodeInfo.setCreateTime(DateUtils.getNowDate());
        return sysNodeInfoMapper.insertSysNodeInfo(sysNodeInfo);
    }

    /**
     * 修改位置详情
     *
     * @param sysNodeInfo 位置详情
     * @return 结果
     */
    @Override
    public int updateSysNodeInfo(SysNodeInfo sysNodeInfo) {
        sysNodeInfo.setUpdateTime(DateUtils.getNowDate());
        return sysNodeInfoMapper.updateSysNodeInfo(sysNodeInfo);
    }

    /**
     * 批量删除位置详情
     *
     * @param nodeIds 需要删除的位置详情主键
     * @return 结果
     */
    @Override
    public int deleteSysNodeInfoByNodeIds(String nodeIds) {
        return sysNodeInfoMapper.deleteSysNodeInfoByNodeIds(Convert.toStrArray(nodeIds));
    }

    /**
     * 删除位置详情信息
     *
     * @param nodeId 位置详情主键
     * @return 结果
     */
    @Override
    public int deleteSysNodeInfoByNodeId(Long nodeId) {
        return sysNodeInfoMapper.deleteSysNodeInfoByNodeId(nodeId);
    }

    /**
     * 导入实验数据
     *
     * @param nodeInfoList 用户数据列表
     * @return 结果
     */
    @Override
    public String importSysNodeInfo(List<SysNodeInfo> nodeInfoList) {
        if (StringUtils.isNull(nodeInfoList) || nodeInfoList.size() == 0) {
            throw new ServiceException("导入实验数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SysNodeInfo nodeInfo : nodeInfoList) {
            try {
                this.insertSysNodeInfo(nodeInfo);
                successNum++;
                successMsg.append("<br/>" + successNum + "导入成功");
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
