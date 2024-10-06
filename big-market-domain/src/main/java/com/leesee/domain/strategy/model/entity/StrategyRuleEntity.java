package com.leesee.domain.strategy.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.leesee.types.common.Constants.*;

/**
 * @Title: StrategyRuleEntity
 * @Author leesee
 * @Package com.leesee.domain.strategy.model.entity
 * @Date 2024/10/6 15:50
 * @description: 策略规则实体对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyRuleEntity {

    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖奖品ID【规则类型为策略，则不需要奖品ID】 */
    private Integer awardId;
    /** 抽象规则类型；1-策略规则、2-奖品规则 */
    private Integer ruleType;
    /** 抽奖规则类型【rule_random - 随机值计算、rule_lock - 抽奖几次后解锁、rule_luck_award - 幸运奖(兜底奖品)】 */
    private String ruleModel;
    /** 抽奖规则比值 */
    private String ruleValue;
    /** 抽奖规则描述 */
    private String ruleDesc;

    /**输入例："4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109" */
    public Map<String, List<Integer>> getRuleWeightValues() {
        if (!"rule_weight".equals(ruleModel)) return null;
        Map<String, List<Integer>>result=new HashMap<>();
        String[] groups = ruleValue.split(SPACE);
        for (String group : groups) {
            String[] parts = group.split(COLON);
            String[] values = parts[1].split(SPLIT);
            List<Integer>valueList=new ArrayList<>();
            for (String value : values) {
                valueList.add(Integer.valueOf(value));
            }
            result.put(parts[0],valueList);
        }
        return result;
    }

//    public static void main(String[] args) {
//        StrategyRuleEntity strategyRuleEntity = StrategyRuleEntity.builder()
//                .ruleModel("rule_weight")
//                .ruleValue("4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109")
//                .build();
//        strategyRuleEntity.getRuleWeightValues().forEach((key, value) -> {
//            System.out.println(key + " -> " + value);
//        });
//
//    }

}
