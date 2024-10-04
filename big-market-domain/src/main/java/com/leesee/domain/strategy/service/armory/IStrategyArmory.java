package com.leesee.domain.strategy.service.armory;

import com.leesee.domain.strategy.model.entity.StrategyAwardEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: IStrategyArmory
 * @Author leesee
 * @Package com.leesee.domain.strategy.service.armory
 * @Date 2024/10/5 0:52
 * @description: 策略装配库
 */

public interface IStrategyArmory {

    boolean assembleLotteryStrategy(Long strategyId);

    Integer getRandomAwardId(Long StrategyId);
}
