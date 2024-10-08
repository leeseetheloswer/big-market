package com.leesee.test.domain;

import com.leesee.infrastructure.persistent.repository.StrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Title: StrategyRuleRepositoryTest
 * @Author leesee
 * @Package com.leesee.test.infrastructure
 * @Date 2024/10/7 17:54
 * @description: 策略规则仓储测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRepositoryTest {
    @Resource
    StrategyRepository repository;
    @Test
    public void test_queryStrategyRuleValue(){
        String strategyRuleValue = repository.queryStrategyRuleValue(100001L, null, "rule_weight");
        log.info("查询结果：{}",strategyRuleValue);
    }
}
