package com.leesee.test.domain;

import com.leesee.domain.strategy.service.armory.IStrategyArmory;
import lombok.extern.slf4j.Slf4j;
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
    @Test
    public void testAssembleLotteryStrategy(){
        boolean success1 = strategyArmory.assembleLotteryStrategy(100001L);
        log.info("测试结果1：{}",success1);
        boolean success2 = strategyArmory.assembleLotteryStrategy(100002L);
        log.info("测试结果2：{}",success2);
        boolean success3 = strategyArmory.assembleLotteryStrategy(100003L);
        log.info("测试结果3：{}",success3);

    }

    @Test
    public void testGetRandomAwardId(){
        for (int i = 0; i < 100; i++) {
            Integer randomAwardId = strategyArmory.getRandomAwardId(100001L);
            log.info("获取到的随机奖品id：{}",randomAwardId);
        }

    }
}
