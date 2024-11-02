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
    /**
     * 根据策略ID和奖品ID，扣减奖品缓存库存
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     * @return 扣减结果
     */
    Boolean subtractionAwardStock(Long strategyId, Integer awardId);
}
