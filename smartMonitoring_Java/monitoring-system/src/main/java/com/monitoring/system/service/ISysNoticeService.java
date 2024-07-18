package com.monitoring.system.service;

import java.util.List;
import com.monitoring.system.domain.SysNotice;

/**
 *  服务层
 * 

 */
public interface ISysNoticeService
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
     * 查询列表数量
     * @param notice 信息
     * @return 数量
     */
    public int selectNoticeListCount(SysNotice notice);

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
     * 删除信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoticeByIds(String ids);
}
