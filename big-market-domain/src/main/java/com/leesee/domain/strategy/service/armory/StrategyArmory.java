package com.leesee.domain.strategy.service.armory;

import com.leesee.domain.strategy.model.entity.StrategyAwardEntity;
import com.leesee.domain.strategy.repository.IStrategyRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @Title: StrategyArmory
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.armory
 * @Date 2024/10/5 0:56
 * @description: 策略装配库
 */
@Service
public class StrategyArmory implements IStrategyArmory {
    @Resource
    private IStrategyRepository repository;


    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        //1.查询策略配置
        List<StrategyAwardEntity> strategyAwardEntityList = repository.queryStrategyAwardList(strategyId);
        if(null==strategyAwardEntityList||strategyAwardEntityList.isEmpty())return false;
        //2.获取最小概率值
        BigDecimal minRate = strategyAwardEntityList
                .stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        //3.获取概率总和
        BigDecimal totalRate = strategyAwardEntityList
                .stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //4.获取概率范围
        BigDecimal rateRange = totalRate.divide(minRate, 0,RoundingMode.CEILING);
        //5.填充抽奖表
        List<Integer> table=new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntityList) {
            Integer awardId = strategyAwardEntity.getAwardId();
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            for(int i=0;i<awardRate.multiply(rateRange).setScale(0,RoundingMode.CEILING).intValue();i++){
                table.add(awardId);
            }
        }
        //6.打乱table
        Collections.shuffle(table);

        //7.存到redis中
        Map<Integer,Integer> shuffleTable=new HashMap<>();
        for (int i = 0; i < table.size(); i++) {
            shuffleTable.put(i,table.get(i));
        }
        repository.storeStrategyAwardSearchTable(strategyId,shuffleTable.size(),shuffleTable);
        return true;

    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        int rateRange = repository.getRateRange(strategyId);
        // 通过生成的随机值，获取概率值奖品查找表的结果
        return repository.getStrategyAwardAssemble(strategyId, new SecureRandom().nextInt(rateRange));
    }
}
