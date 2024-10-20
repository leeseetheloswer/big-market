package com.leesee.domain.strategy.service.rule.tree.impl;

import com.leesee.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.leesee.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.leesee.domain.strategy.service.rule.tree.factory.DefaultLogicTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Title: RuleLockLogicTreeNode
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.rule.tree.impl
 * @Date 2024/10/15 2:15
 * @description: 次数锁节点
 */

@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultLogicTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId) {

        return DefaultLogicTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }
}
