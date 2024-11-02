package com.leesee.domain.strategy.service.raffle;

import com.leesee.domain.strategy.model.vo.RuleTreeVO;
import com.leesee.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import com.leesee.domain.strategy.model.vo.StrategyAwardStockKeyVO;
import com.leesee.domain.strategy.repository.IStrategyRepository;
import com.leesee.domain.strategy.service.AbstractRaffleStrategy;
import com.leesee.domain.strategy.service.armory.IStrategyDispatch;
import com.leesee.domain.strategy.service.rule.chain.ILogicChain;
import com.leesee.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import com.leesee.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import com.leesee.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Title: DefaultRaffleStrategy
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.raffle
 * @Date 2024/10/8 23:04
 * @description: 默认抽奖策略
 */
@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {

    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch, DefaultChainFactory chainFactory, DefaultTreeFactory defaultTreeFactory) {
        super(repository, strategyDispatch,chainFactory, defaultTreeFactory);
    }

    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(String userId, Long strategyId) {
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        return logicChain.logic(userId, strategyId);

    }

    @Override
    public DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);
        if (null == strategyAwardRuleModelVO) {
            return DefaultTreeFactory.StrategyAwardVO.builder().awardId(awardId).build();
        }
        RuleTreeVO ruleTreeVO = repository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModels());
        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);
        return treeEngine.process(userId, strategyId, awardId);


    }


    @Override
    public StrategyAwardStockKeyVO takeQueueValue()  {
        return repository.takeQueueValue();
    }

    @Override
    public void updateStrategyAwardStock(Long strategyId, Integer awardId) {
        repository.updateStrategyAwardStock(strategyId, awardId);
    }
}
