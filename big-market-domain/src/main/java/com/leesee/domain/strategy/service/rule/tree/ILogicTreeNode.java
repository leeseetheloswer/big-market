package com.leesee.domain.strategy.service.rule.tree;

import com.leesee.domain.strategy.service.rule.tree.factory.DefaultLogicTreeFactory;

/**
 * @Title: ILogicTreeNode
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.rule.tree
 * @Date 2024/10/15 2:12
 * @description: 规则树接口
 */
public interface ILogicTreeNode {
    DefaultLogicTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId);
}
