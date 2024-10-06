package com.leesee.domain.strategy.service.armory;

/**
 * @Title: IStrategyDispatch
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.armory
 * @Date 2024/10/6 14:51
 * @description: 策略装配调度接口
 */
public interface IStrategyDispatch {
    Integer getRandomAwardId(Long StrategyId);
    Integer getRandomAwardId(Long StrategyId,String ruleWeightValue);
}
