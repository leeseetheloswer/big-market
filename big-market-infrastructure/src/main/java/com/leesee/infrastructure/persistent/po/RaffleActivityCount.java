package com.leesee.infrastructure.persistent.po;

import java.util.Date;

/**
 * @Title: RaffleActivityCount
 * @Author leesee
 * @Package com.leesee.infrastructure.persistent.po
 * @Date 2025/11/22 18:52
 * @description:
 */
public class RaffleActivityCount {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 活动次数编号
     */
    private Long activityCountId;

    /**
     * 总次数
     */
    private Integer totalCount;

    /**
     * 日次数
     */
    private Integer dayCount;

    /**
     * 月次数
     */
    private Integer monthCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
