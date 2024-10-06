package com.leesee.domain.strategy.service.armory;

import com.leesee.domain.strategy.model.entity.StrategyAwardEntity;
import com.leesee.domain.strategy.model.entity.StrategyEntity;
import com.leesee.domain.strategy.model.entity.StrategyRuleEntity;
import com.leesee.domain.strategy.repository.IStrategyRepository;
import com.leesee.types.exception.AppException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

import static com.leesee.types.enums.ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL;

/**
 * @Title: StrategyArmory
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.armory
 * @Date 2024/10/5 0:56
 * @description: 策略装配库
 */
@Service
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch {
    @Resource
    private IStrategyRepository repository;


    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        //1.查询策略配置
        List<StrategyAwardEntity> strategyAwardEntityList = repository.queryStrategyAwardList(strategyId);
        if (null == strategyAwardEntityList || strategyAwardEntityList.isEmpty()) return false;
        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntityList);
        //2.权重策略配置
        StrategyEntity strategyEntity = repository.queryStrategyEntityByStrategyId(strategyId);
        String ruleWeight = strategyEntity.getRuleWeight();
        if (null == ruleWeight) {
            return true;
        }
        StrategyRuleEntity strategyRule = repository.queryStrategyRule(strategyId, ruleWeight);
        if (null == strategyRule) {
            throw new AppException(STRATEGY_RULE_WEIGHT_IS_NULL.getCode(), STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }
        Map<String, List<Integer>> ruleWeightValues = strategyRule.getRuleWeightValues();
        Set<String> keySet = ruleWeightValues.keySet();
        for (String key : keySet) {
            ArrayList<StrategyAwardEntity> strategyAwardEntityArrayListClone = new ArrayList<>(strategyAwardEntityList);
            strategyAwardEntityArrayListClone.removeIf(strategyAwardEntity ->
                    !ruleWeightValues.get(key).contains(strategyAwardEntity.getAwardId()));
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key),strategyAwardEntityArrayListClone);
        }
        return true;

    }

    private void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntityList) {
        //1.获取最小概率值
        BigDecimal minRate = strategyAwardEntityList
                .stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        //2.获取概率总和
        BigDecimal totalRate = strategyAwardEntityList
                .stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //3.获取概率范围
        BigDecimal rateRange = totalRate.divide(minRate, 0, RoundingMode.CEILING);
        //4.填充抽奖表
        List<Integer> table = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntityList) {
            Integer awardId = strategyAwardEntity.getAwardId();
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            for (int i = 0; i < awardRate.multiply(rateRange).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                table.add(awardId);
            }
        }
        //5.打乱table
        Collections.shuffle(table);

        //6.存到redis中
        Map<Integer, Integer> shuffleTable = new HashMap<>();
        for (int i = 0; i < table.size(); i++) {
            shuffleTable.put(i, table.get(i));
        }
        repository.storeStrategyAwardSearchTable(key, shuffleTable.size(), shuffleTable);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange = repository.getRateRange(strategyId);
        // 通过生成的随机值，获取概率值奖品查找表的结果
        return repository.getStrategyAwardAssemble(strategyId, new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key=String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        return  getRandomAwardIdByKey(key);
    }
    private Integer getRandomAwardIdByKey(String key) {
        int rateRange = repository.getRateRange(key);
        // 通过生成的随机值，获取概率值奖品查找表的结果
        return repository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));
    }


}
