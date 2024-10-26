package com.leesee.domain.strategy.service.rule.chain.factory;

import com.leesee.domain.strategy.model.entity.StrategyEntity;
import com.leesee.domain.strategy.repository.IStrategyRepository;
import com.leesee.domain.strategy.service.rule.chain.ILogicChain;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Title: DefaultFactory
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.rule.chain.factory
 * @Date 2024/10/14 23:17
 * @description: 默认责任链工厂
 */
@Service
public class DefaultChainFactory {
    private final Map<String, ILogicChain> logicChainMap;
    private final IStrategyRepository repository;

    public DefaultChainFactory(Map<String, ILogicChain> logicChainMap, IStrategyRepository repository) {
        this.logicChainMap = logicChainMap;
        this.repository = repository;
    }

    public ILogicChain openLogicChain(Long strategyId){
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategy.ruleModels();
        if(null==ruleModels||0== ruleModels.length)return logicChainMap.get("default");
        ILogicChain logicChain = logicChainMap.get(ruleModels[0]);
        ILogicChain current=logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain next = logicChainMap.get(ruleModels[i]);
            current=current.appendNext(next);
        }
        current.appendNext(logicChainMap.get("default"));
        return logicChain;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /**  */
        private String logicModel;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }
}
