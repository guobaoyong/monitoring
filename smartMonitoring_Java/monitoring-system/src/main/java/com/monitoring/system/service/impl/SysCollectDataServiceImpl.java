package com.monitoring.system.service.impl;

import java.util.List;

import com.monitoring.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monitoring.system.mapper.SysCollectDataMapper;
import com.monitoring.system.domain.SysCollectData;
import com.monitoring.system.service.ISysCollectDataService;
import com.monitoring.common.core.text.Convert;

/**
 * 采集记录Service业务层处理
 *
 * @date 2023-03-16
 */
@Service
public class SysCollectDataServiceImpl implements ISysCollectDataService {

    @Autowired
    private SysCollectDataMapper sysCollectDataMapper;

    /**
     * 查询采集记录
     *
     * @param collectId 采集记录主键
     * @return 采集记录
     */
    @Override
    public SysCollectData selectSysCollectDataByCollectId(Long collectId) {
        return sysCollectDataMapper.selectSysCollectDataByCollectId(collectId);
    }

    /**
     * 查询采集记录列表
     *
     * @param sysCollectData 采集记录
     * @return 采集记录
     */
    @Override
    public List<SysCollectData> selectSysCollectDataList(SysCollectData sysCollectData) {
        return sysCollectDataMapper.selectSysCollectDataList(sysCollectData);
    }

    /**
     * 新增采集记录
     *
     * @param sysCollectData 采集记录
     * @return 结果
     */
    @Override
    public int insertSysCollectData(SysCollectData sysCollectData) {
        sysCollectData.setCreateTime(DateUtils.getNowDate());
        return sysCollectDataMapper.insertSysCollectData(sysCollectData);
    }

    /**
     * 修改采集记录
     *
     * @param sysCollectData 采集记录
     * @return 结果
     */
    @Override
    public int updateSysCollectData(SysCollectData sysCollectData) {
        sysCollectData.setUpdateTime(DateUtils.getNowDate());
        return sysCollectDataMapper.updateSysCollectData(sysCollectData);
    }

    /**
     * 批量删除采集记录
     *
     * @param collectIds 需要删除的采集记录主键
     * @return 结果
     */
    @Override
    public int deleteSysCollectDataByCollectIds(String collectIds) {
        return sysCollectDataMapper.deleteSysCollectDataByCollectIds(Convert.toStrArray(collectIds));
    }

    /**
     * 删除采集记录信息
     *
     * @param collectId 采集记录主键
     * @return 结果
     */
    @Override
    public int deleteSysCollectDataByCollectId(Long collectId) {
        return sysCollectDataMapper.deleteSysCollectDataByCollectId(collectId);
    }

    @Override
    public List<SysCollectData> selectSysCollectDataListByBOthNull() {
        return sysCollectDataMapper.selectSysCollectDataListByBOthNull();
    }

    @Override
    public int selectCount() {
        return sysCollectDataMapper.selectCount();
    }
}
