package com.leesee.domain.strategy.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: StrategyAwardStockKeyVO
 * @Author leesee
 * @Package com.leesee.domain.strategy.model.vo
 * @Date 2024/10/28 15:47
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardStockKeyVO {
    // 策略ID
    private Long strategyId;
    // 奖品ID
    private Integer awardId;


}
