package com.leesee.domain.strategy.repository;

import com.leesee.domain.strategy.model.entity.StrategyAwardEntity;
import com.leesee.domain.strategy.model.entity.StrategyEntity;
import com.leesee.domain.strategy.model.entity.StrategyRuleEntity;
import com.leesee.domain.strategy.model.vo.RuleTreeVO;
import com.leesee.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import com.leesee.domain.strategy.model.vo.StrategyAwardStockKeyVO;

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



    void storeStrategyAwardSearchTable(String key, Integer rateRange, Map<Integer, Integer> shuffleTable);

    Integer getRateRange(Long strategyId);
    Integer getRateRange(String key);

    Integer getStrategyAwardAssemble(Long strategyId, int key);
    Integer getStrategyAwardAssemble(String key, int rateKey);

    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel);
    String queryStrategyRuleValue(Long strategyId,  String ruleModel);

    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

    StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId);

    RuleTreeVO queryRuleTreeVOByTreeId(String treeId);

    /**
     * 缓存奖品库存
     * @param cacheKey key
     * @param awardCount 奖品数量
     */
    void cacheStrategyAwardCount(String cacheKey, Integer awardCount);

    /**
     * 从缓存中扣减奖品库存
     * @param cacheKey key
     */
    Boolean subtractionAwardStock(String cacheKey);

    void awardStockConsumeSendQueue(StrategyAwardStockKeyVO build);

    StrategyAwardStockKeyVO takeQueueValue();

    void updateStrategyAwardStock(Long strategyId, Integer awardId);
}
