package com.monitoring.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.monitoring.common.core.text.Convert;
import com.monitoring.system.domain.SysNotice;
import com.monitoring.system.mapper.SysNoticeMapper;
import com.monitoring.system.service.ISysNoticeService;

/**
 *  服务层实现
 * 

 * @date 2018-06-25
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService
{
    @Autowired
    private SysNoticeMapper noticeMapper;

    /**
     * 查询信息
     * 
     * @param noticeId ID
     * @return 信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询列表
     * 
     * @param notice 信息
     * @return 集合
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    @Override
    public int selectNoticeListCount(SysNotice notice) {
        return noticeMapper.selectNoticeListCount(notice);
    }

    /**
     * 新增
     * 
     * @param notice 信息
     * @return 结果
     */
    @Override
    public int insertNotice(SysNotice notice)
    {
        return noticeMapper.insertNotice(notice);
    }

    /**
     * 修改
     * 
     * @param notice 信息
     * @return 结果
     */
    @Override
    public int updateNotice(SysNotice notice)
    {
        return noticeMapper.updateNotice(notice);
    }

    /**
     * 删除对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(String ids)
    {
        return noticeMapper.deleteNoticeByIds(Convert.toStrArray(ids));
    }
}
