package com.leesee.infrastructure.persistent.dao;

import com.leesee.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Title: IStrategyMapper
 * @Author leesee
 * @Package com.leesee.infrastructure.persistent.dao
 * @Date 2024/10/4 15:09
 * @description: 策略mapper
 */
@Mapper
public interface IStrategyMapper {
    Strategy queryStrategyByStrategyId(Long strategyId);
}
