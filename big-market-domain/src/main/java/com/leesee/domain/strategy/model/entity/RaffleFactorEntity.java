package com.leesee.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: RaffleFactorEntity
 * @Author leesee
 * @Package com.leesee.domain.strategy.model.entity
 * @Date 2024/10/7 15:13
 * @description: 抽奖计算因子实体
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleFactorEntity {
    /** 用户id*/
    private String userId;
    /** 策略id*/
    private Long strategyId;
}
