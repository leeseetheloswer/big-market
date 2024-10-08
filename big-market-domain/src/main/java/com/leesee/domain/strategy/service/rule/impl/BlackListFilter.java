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

/**
 * @Title: BlackListFilter
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.rule.impl
 * @Date 2024/10/7 16:04
 * @description: 黑名单过滤
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_BLACKLIST)
public class BlackListFilter implements ILogicFilter<RuleActionEntity.RaffleBeforeEntity> {
    @Resource
    IStrategyRepository repository;
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("黑名单过滤：{}",ruleMatterEntity);
        String ruleValue= repository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(),ruleMatterEntity.getAwardId(),ruleMatterEntity.getRuleModel());
        String[] split = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.valueOf(split[0]);
        String[] blackListIds = split[1].split(Constants.SPLIT);
        for (String blackListId : blackListIds) {
            if(ruleMatterEntity.getUserId().equals(blackListId)){
                return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                        .ruleModel(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode())
                        .data(RuleActionEntity.RaffleBeforeEntity.builder()
                                .strategyId(ruleMatterEntity.getStrategyId())
                                .awardId(awardId)
                                .build())
                        .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                        .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                        .build();
            }
        }

        return RuleActionEntity.<RuleActionEntity.RaffleBeforeEntity>builder()
                .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                .build();
    }
}
