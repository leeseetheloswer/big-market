package com.leesee.domain.strategy.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Title: StrategyAwardEntity
 * @Author leesee
 * @Package com.leesee.domain.strategy.model.entity
 * @Date 2024/10/5 1:05
 * @description: 策略奖品信息实体对象
 */
@Data
@AllArgsConstructor
@Builder
public class StrategyAwardEntity {
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖奖品ID - 内部流转使用 */
    private Integer awardId;
    /** 奖品库存总量 */
    private Integer awardCount;
    /** 奖品库存剩余 */
    private Integer awardCountSurplus;
    /** 奖品中奖概率 */
    private BigDecimal awardRate;
}
