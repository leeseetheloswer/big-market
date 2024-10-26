package com.leesee.domain.strategy.service.rule.tree.factory.engine;

import com.leesee.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @Title: IDecisionTreeEngine
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.rule.tree.factory.engine
 * @Date 2024/10/15 2:25
 * @description: 规则树组合接口
 */
public interface IDecisionTreeEngine {

    DefaultTreeFactory.StrategyAwardVO process(String userId, Long strategyId, Integer awardId);
}
