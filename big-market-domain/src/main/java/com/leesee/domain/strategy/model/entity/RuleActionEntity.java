package com.leesee.domain.strategy.model.entity;

import com.leesee.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import lombok.*;

/**
 * @Title: RuleActionEntity
 * @Author leesee
 * @Package com.leesee.domain.strategy.model.entity
 * @Date 2024/10/7 15:33
 * @description: 规则动作实体
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RuleActionEntity<T extends RuleActionEntity.RaffleEntity> {

    public String code=RuleLogicCheckTypeVO.ALLOW.getCode();
    public String info=RuleLogicCheckTypeVO.ALLOW.getInfo();
    public String ruleModel;
    public T data;
    public static class RaffleEntity{

    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RaffleBeforeEntity extends  RaffleEntity{
        /**
         * 策略ID
         */
        private Long strategyId;

        /**
         * 权重值Key；用于抽奖时可以选择权重抽奖。
         */
        private String ruleWeightValueKey;

        /**
         * 奖品ID；
         */
        private Integer awardId;
    }

    public static class RafflingEntity extends  RaffleEntity{

    }

    public static class RaffleAfterEntity extends  RaffleEntity{

    }
}
