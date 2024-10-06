package com.leesee.test.domain;

import com.leesee.domain.strategy.service.armory.IStrategyArmory;
import com.leesee.domain.strategy.service.armory.IStrategyDispatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Title: StrategyArmoryTest
 * @Author leesee
 * @Package com.leesee.test.domain
 * @Date 2024/10/5 2:59
 * @description: 策略奖品装配测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryTest {
    @Resource
    IStrategyArmory strategyArmory;
    @Resource
    IStrategyDispatch strategyDispatch;
    @Before
    public void testAssembleLotteryStrategy(){
        boolean success = strategyArmory.assembleLotteryStrategy(100001L);
        log.info("装配结果：{}",success);


    }

    @Test
    public void testGetRandomAwardId(){
        for (int i = 0; i < 100; i++) {
            Integer randomAwardId = strategyDispatch.getRandomAwardId(100001L);
            log.info("获取到的随机奖品id：{}",randomAwardId);
        }

    }

    @Test
    public void testGetRandomAwardId_ruleWeightValue(){


            log.info("4000->获取到的随机奖品id：{}",strategyDispatch.getRandomAwardId(100001L,"4000"));
            log.info("5000->获取到的随机奖品id：{}",strategyDispatch.getRandomAwardId(100001L,"5000"));
            log.info("6000->获取到的随机奖品id：{}",strategyDispatch.getRandomAwardId(100001L,"6000"));


    }


}
