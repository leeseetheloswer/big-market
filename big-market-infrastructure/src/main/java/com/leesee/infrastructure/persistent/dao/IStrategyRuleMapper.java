package com.leesee.infrastructure.persistent.dao;

import com.leesee.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Title: IStrategyRuleMapper
 * @Author leesee
 * @Package com.leesee.infrastructure.persistent.dao
 * @Date 2024/10/4 15:09
 * @description: 策略规则mapper
 */
@Mapper
public interface IStrategyRuleMapper {
    StrategyRule queryStrategyRule(Long strategyId, String ruleModel);
    String queryStrategyRuleValue(StrategyRule strategyRuleReq);
}
