package com.leesee.domain.strategy.service.rule;

import com.leesee.domain.strategy.model.entity.RuleActionEntity;
import com.leesee.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @Title: ILogicFilter
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.rule
 * @Date 2024/10/7 15:25
 * @description: 逻辑过滤器接口
 */
public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {
    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);
}
