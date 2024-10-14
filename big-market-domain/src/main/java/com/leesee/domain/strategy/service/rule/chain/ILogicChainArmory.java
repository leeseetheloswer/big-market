package com.leesee.domain.strategy.service.rule.chain;

/**
 * @author leesee
 * @description 责任链装配
 * @create 2024-01-20 11:53
 */
public interface ILogicChainArmory {

    ILogicChain next();

    ILogicChain appendNext(ILogicChain next);

}
