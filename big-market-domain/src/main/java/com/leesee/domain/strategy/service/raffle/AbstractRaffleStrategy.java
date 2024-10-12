package com.leesee.domain.strategy.service.raffle;

import com.leesee.domain.strategy.model.entity.RaffleAwardEntity;
import com.leesee.domain.strategy.model.entity.RaffleFactorEntity;
import com.leesee.domain.strategy.model.entity.RuleActionEntity;
import com.leesee.domain.strategy.model.entity.StrategyEntity;
import com.leesee.domain.strategy.model.vo.RuleLogicCheckTypeVO;
import com.leesee.domain.strategy.model.vo.StrategyAwardRuleModelVO;
import com.leesee.domain.strategy.repository.IStrategyRepository;
import com.leesee.domain.strategy.service.IRaffleStrategy;
import com.leesee.domain.strategy.service.armory.IStrategyDispatch;
import com.leesee.domain.strategy.service.rule.factory.DefaultLogicFactory;
import com.leesee.types.enums.ResponseCode;
import com.leesee.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Title: AbstractRaffleStrategy
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.raffle
 * @Date 2024/10/7 15:21
 * @description: 抽奖策略抽象类
 */
@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {

    // 策略仓储服务
    protected IStrategyRepository repository;
    // 策略调度服务 -> 只负责抽奖处理，通过新增接口的方式，隔离职责，不需要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatch strategyDispatch;

    public AbstractRaffleStrategy(IStrategyRepository repository, IStrategyDispatch strategyDispatch) {
        this.repository = repository;
        this.strategyDispatch = strategyDispatch;
    }

    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        //1.参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();

        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        // 2. 策略查询
        StrategyEntity strategy = repository.queryStrategyEntityByStrategyId(strategyId);

        //3.抽奖前 - 规则过滤
//        RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> ruleActionEntity = this.doCheckRaffleBeforeLogic(RaffleFactorEntity.builder().userId(userId).strategyId(strategyId).build(), strategy.ruleModels());
//
//        if (ruleActionEntity.getCode().equals(RuleLogicCheckTypeVO.TAKE_OVER.getCode())) {
//            if (ruleActionEntity.ruleModel.equals(DefaultLogicFactory.LogicModel.RULE_BLACKLIST.getCode())) {
//                return RaffleAwardEntity.builder().awardId(ruleActionEntity.getData().getAwardId()).build();
//
//            }
//
//            if (ruleActionEntity.ruleModel.equals(DefaultLogicFactory.LogicModel.RULE_WEIGHT.getCode())) {
//                // 权重根据返回的信息进行抽奖
//                RuleActionEntity.RaffleBeforeEntity raffleBeforeEntity = ruleActionEntity.getData();
//                String ruleWeightValueKey = raffleBeforeEntity.getRuleWeightValueKey();
//                Integer awardId = strategyDispatch.getRandomAwardId(strategyId, ruleWeightValueKey);
//                return RaffleAwardEntity.builder()
//                        .awardId(awardId)
//                        .build();
//            }
//        }
        //4.默认抽奖流程
        Integer awardId = strategyDispatch.getRandomAwardId(strategyId);
        //5.查询奖品规则「抽奖中（拿到奖品ID时，过滤规则）、抽奖后（扣减完奖品库存后过滤，抽奖中拦截和无库存则走兜底）」
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = repository.queryStrategyAwardRuleModelVO(strategyId, awardId);

        //6.抽奖中 - 规则过滤
        RuleActionEntity<RuleActionEntity.RaffleCenterEntity> raffleCenterEntityRuleActionEntity = this.doCheckRaffleCenterLogic(RaffleFactorEntity.builder()
                .userId(userId)
                .strategyId(strategyId)
                .awardId(awardId).build(), strategyAwardRuleModelVO.raffleCenterRuleModelList());
        if (RuleLogicCheckTypeVO.TAKE_OVER.getCode().equals(raffleCenterEntityRuleActionEntity.getCode())){
            log.info("【临时日志】中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。");
            return RaffleAwardEntity.builder()
                    .awardDesc("中奖中规则拦截，通过抽奖后规则 rule_luck_award 走兜底奖励。")
                    .build();
        }
        return RaffleAwardEntity.builder().awardId(awardId).build();
    }

    protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity raffleFactorEntity, String... logics);

    protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);
}
