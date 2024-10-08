package com.leesee.domain.strategy.service.rule.impl;

import com.leesee.domain.strategy.model.entity.RuleActionEntity;
import com.leesee.domain.strategy.model.entity.RuleMatterEntity;
import com.leesee.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.leesee.domain.strategy.repository.IStrategyRepository;
import com.leesee.domain.strategy.service.annotation.LogicStrategy;
import com.leesee.domain.strategy.service.rule.ILogicFilter;
import com.leesee.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.leesee.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Title: RuleWeightFilter
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.rule.impl
 * @Date 2024/10/8 18:12
 * @description: 权重过滤器
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_WEIGHT)
public class RuleWeightFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {
    @Resource
    IStrategyRepository repository;

    Long userScore = 4500L;

    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则权重过滤：{}", ruleMatterEntity);
        String userId = ruleMatterEntity.getUserId();
        String ruleModel = ruleMatterEntity.getRuleModel();
        Long strategyId = ruleMatterEntity.getStrategyId();
        Integer awardId = ruleMatterEntity.getAwardId();

        String strategyRuleValue = repository.queryStrategyRuleValue(strategyId, awardId, ruleModel);
        Map<Long, String> analyticalValue = getAnalyticalValue(strategyRuleValue);
        if (analyticalValue == null) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .build();
        }
        //转换keys值，并默认排序
        List<Long> keys = new ArrayList<>(analyticalValue.keySet());
        Collections.sort(keys);
        //找到最小符合的积分值
        Long nextValue = keys.stream().filter(key -> userScore >= key).findFirst().orElse(null);
        if (null != nextValue) {
            return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                    .data(RuleActionEntity.RaffleBeforeEntity.builder()
                            .strategyId(strategyId)
                            .ruleWeightValueKey(String.valueOf(nextValue))
                            .build())
                    .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                    .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                    .ruleModel(DefaultLogicFactory.LogicModel.RULE_WEIGHT.getCode())
                    .build();
        }
        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .build();
    }

    private Map<Long, String> getAnalyticalValue(String ruleValue) {
        String[] ruleValueGroups = ruleValue.split(Constants.SPACE);
        Map<Long, String> ruleValueMap = new HashMap<>();
        for (String ruleValueKey : ruleValueGroups) {
            // 检查输入是否为空
            if (ruleValueKey == null || ruleValueKey.isEmpty()) {
                return ruleValueMap;
            }
            // 分割字符串以获取键和值
            String[] parts = ruleValueKey.split(Constants.COLON);
            if (parts.length != 2) {
                throw new IllegalArgumentException("rule_weight rule_rule invalid input format" + ruleValueKey);
            }
            ruleValueMap.put(Long.parseLong(parts[0]), ruleValueKey);
        }
        return ruleValueMap;
    }
}
