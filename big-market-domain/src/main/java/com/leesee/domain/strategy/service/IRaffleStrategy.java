package com.leesee.domain.strategy.service;

import com.leesee.domain.strategy.model.entity.RaffleAwardEntity;
import com.leesee.domain.strategy.model.entity.RaffleFactorEntity;

/**
 * @Title: IRaffleStrategy
 * @Author leesee
 * @Package com.leesee.domain.strategy.service
 * @Date 2024/10/7 15:11
 * @description: 抽奖策略接口
 */
public interface IRaffleStrategy {
    RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity);
}
