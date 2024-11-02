package com.leesee.domain.strategy.service.rule.tree.impl;

import com.leesee.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.leesee.domain.strategy.model.vo.StrategyAwardStockKeyVO;
import com.leesee.domain.strategy.repository.IStrategyRepository;
import com.leesee.domain.strategy.service.armory.IStrategyDispatch;
import com.leesee.domain.strategy.service.rule.tree.ILogicTreeNode;
import com.leesee.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Title: RuleStockLogicTreeNode
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.rule.tree.impl
 * @Date 2024/10/15 2:18
 * @description: 库存规则树节点
 */
@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {


    // 用户抽奖次数，后续完成这部分流程开发的时候，从数据库/Redis中读取
    private Long userRaffleCount = 10L;

    @Resource
    private IStrategyDispatch strategyDispatch;

    @Resource
    private IStrategyRepository repository;

    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId,String ruleValue) {
        log.info("规则过滤-库存扣减 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
        // 扣减库存
        Boolean status = strategyDispatch.subtractionAwardStock(strategyId, awardId);

        //判断扣减成功与否
        if (status) {
            repository.awardStockConsumeSendQueue(StrategyAwardStockKeyVO.builder()
                    .strategyId(strategyId)
                    .awardId(awardId)
                    .build());

            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                    .strategyAwardData(
                            DefaultTreeFactory.StrategyAwardVO.builder()
                                    .awardId(awardId)
                                    .awardRuleValue(ruleValue)
                                    .build()
                    )
                    .build();
        }


        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }
}
