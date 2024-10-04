package com.leesee.infrastructure.persistent.dao;

import com.leesee.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Title: IStrategyAwardMapper
 * @Author leesee
 * @Package com.leesee.infrastructure.persistent.dao
 * @Date 2024/10/4 15:11
 * @description: 策略-奖品mapper
 */
@Mapper
public interface IStrategyAwardMapper {
    List<StrategyAward> queryStrategyAwardList();
    List<StrategyAward> queryStrategyAwardListByStrategyId(Long strategyId);
}
