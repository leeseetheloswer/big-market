package com.leesee.domain.strategy.service.rule.tree.impl;

import com.leesee.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.leesee.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.leesee.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.leesee.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Title: RuleLuckAwardLogicTreeNode
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.rule.tree.impl
 * @Date 2024/10/15 2:17
 * @description: 幸运奖
 */
@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {


    // 用户抽奖次数，后续完成这部分流程开发的时候，从数据库/Redis中读取
    private Long userRaffleCount = 10L;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤-兜底奖品 userId:{} strategyId:{} awardId:{} ruleValue:{}", userId, strategyId, awardId, ruleValue);
        String[] split = ruleValue.split(Constants.COLON);

        if (split.length == 0) {
            log.error("规则过滤-兜底奖品，兜底奖品未配置告警 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
            throw new RuntimeException("兜底奖品未配置 " + ruleValue);


        }

        //兜底奖励配置
        Integer luckAwardId = Integer.valueOf(split[0]);
        String awardRuleValue = split.length > 1 ? split[1] : "";
        //返回兜底奖品
        log.info("规则过滤-兜底奖品 userId:{} strategyId:{} awardId:{} awardRuleValue:{}", userId, strategyId, luckAwardId, awardRuleValue);
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardData(DefaultTreeFactory.StrategyAwardVO.builder()
                        .awardId(luckAwardId)
                        .awardRuleValue(awardRuleValue)
                        .build())
                .build();
    }
}
