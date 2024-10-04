package com.leesee.domain.strategy.repository;

import com.leesee.domain.strategy.model.entity.StrategyAwardEntity;

import java.util.List;
import java.util.Map;

/**
 * @Title: IStrategyRespository
 * @Author leesee
 * @Package com.leesee.domain.strategy.repository
 * @Date 2024/10/5 0:59
 * @description: 策略仓储接口
 */
public interface IStrategyRepository {

    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);



    void storeStrategyAwardSearchTable(Long strategyId, Integer rateRange, Map<Integer, Integer> shuffleTable);

    Integer getRateRange(Long strategyId);

    Integer getStrategyAwardAssemble(Long strategyId, int key);
}
