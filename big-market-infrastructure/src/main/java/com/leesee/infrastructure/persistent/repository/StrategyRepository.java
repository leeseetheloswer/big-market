package com.leesee.infrastructure.persistent.repository;

import com.leesee.domain.strategy.model.entity.StrategyAwardEntity;
import com.leesee.domain.strategy.model.entity.StrategyEntity;
import com.leesee.domain.strategy.model.entity.StrategyRuleEntity;
import com.leesee.domain.strategy.repository.IStrategyRepository;
import com.leesee.infrastructure.persistent.dao.IStrategyAwardMapper;
import com.leesee.infrastructure.persistent.dao.IStrategyMapper;
import com.leesee.infrastructure.persistent.dao.IStrategyRuleMapper;
import com.leesee.infrastructure.persistent.po.Strategy;
import com.leesee.infrastructure.persistent.po.StrategyAward;
import com.leesee.infrastructure.persistent.po.StrategyRule;
import com.leesee.infrastructure.persistent.redis.IRedisService;
import com.leesee.types.common.Constants;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title: StrategyRepository
 * @Author leesee
 * @Package com.leesee.infrastructure.persistent.repository
 * @Date 2024/10/5 1:02
 * @description: 策略仓储实现
 */
@Repository
public class StrategyRepository implements IStrategyRepository {
    @Resource
    private IStrategyAwardMapper strategyAwardMapper;
    @Resource
    private IStrategyMapper strategyMapper;
    @Resource
    private IStrategyRuleMapper strategyRuleMapper;
    @Resource
    private IRedisService redisService;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntityList = redisService.getValue(cacheKey);
        if (null != strategyAwardEntityList && !strategyAwardEntityList.isEmpty()) {
            return strategyAwardEntityList;
        }

        List<StrategyAward> strategyAwards = strategyAwardMapper.queryStrategyAwardListByStrategyId(strategyId);
        strategyAwardEntityList = new ArrayList<>(strategyAwards.size());
        for (StrategyAward strategyAward : strategyAwards) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                    .strategyId(strategyAward.getStrategyId())
                    .awardId(strategyAward.getAwardId())
                    .awardCount(strategyAward.getAwardCount())
                    .awardCountSurplus(strategyAward.getAwardCountSurplus())
                    .awardRate(strategyAward.getAwardRate())
                    .build();
            strategyAwardEntityList.add(strategyAwardEntity);
        }
        redisService.setValue(cacheKey, strategyAwardEntityList);
        return strategyAwardEntityList;
    }

    @Override
    public void storeStrategyAwardSearchTable(String key, Integer rateRange, Map<Integer, Integer> shuffleTable) {
        // 1. 存储抽奖策略范围值，如10000，用于生成1000以内的随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key, rateRange);
        // 2. 存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key);
        cacheRateTable.putAll(shuffleTable);
    }

    @Override
    public Integer getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key);
    }

    @Override
    public Integer getRateRange(Long strategyId) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId);
    }

    @Override
    public Integer getStrategyAwardAssemble(Long strategyId, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId, rateKey);
    }

    @Override
    public Integer getStrategyAwardAssemble(String key, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY .concat(key), rateKey);
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);
        if (null != strategyEntity) {
            return strategyEntity;
        }
        Strategy strategy = strategyMapper.queryStrategyByStrategyId(strategyId);
        strategyEntity = StrategyEntity.builder()
                .strategyId(strategy.getStrategyId())
                .strategyDesc(strategy.getStrategyDesc())
                .ruleModels(strategy.getRuleModels())
                .build();
        redisService.setValue(cacheKey, strategyEntity);
        return strategyEntity;
    }

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        StrategyRule strategyRule = strategyRuleMapper.queryStrategyRule(strategyId, ruleModel);
        return StrategyRuleEntity.builder()
                .strategyId(strategyRule.getStrategyId())
                .awardId(strategyRule.getAwardId())
                .ruleType(strategyRule.getRuleType())
                .ruleModel(strategyRule.getRuleModel())
                .ruleValue(strategyRule.getRuleValue())
                .ruleDesc(strategyRule.getRuleDesc())
                .build();
    }
}
