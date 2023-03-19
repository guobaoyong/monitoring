package com.monitoring.system.mapper;

import java.util.List;
import com.monitoring.system.domain.SysNotice;

/**
 *  数据层
 * 

 */
public interface SysNoticeMapper
{
    /**
     * 查询信息
     * 
     * @param noticeId ID
     * @return 信息
     */
    public SysNotice selectNoticeById(Long noticeId);

    /**
     * 查询列表
     * 
     * @param notice 信息
     * @return 集合
     */
    public List<SysNotice> selectNoticeList(SysNotice notice);

    /**
     * 新增
     * 
     * @param notice 信息
     * @return 结果
     */
    public int insertNotice(SysNotice notice);

    /**
     * 修改
     * 
     * @param notice 信息
     * @return 结果
     */
    public int updateNotice(SysNotice notice);

    /**
     * 批量删除
     * 
     * @param noticeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoticeByIds(String[] noticeIds);

    /**
     * 查询传感器数量
     *
     * @return 结果
     */
    int selectCount();

}