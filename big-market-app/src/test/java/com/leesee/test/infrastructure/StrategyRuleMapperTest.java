package com.leesee.test.infrastructure;

import com.leesee.infrastructure.persistent.dao.IStrategyRuleMapper;
import com.leesee.infrastructure.persistent.po.StrategyRule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Title: StrategyRuleMapperTest
 * @Author leesee
 * @Package com.leesee.test.infrastructure
 * @Date 2024/10/13 1:19
 * @description: 策略规则mapper测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyRuleMapperTest {
    @Resource
    IStrategyRuleMapper strategyRuleMapper;

    @Test
   public void testRuleModel(){
        String ruleWeight = strategyRuleMapper.testRuleModelMapping(100001L, "rule_weight");
        log.info("测试结果：{}",ruleWeight);
    }

    @Test
    public void test_queryStrategyRule(){
        StrategyRule strategyRule = strategyRuleMapper.queryStrategyRule(100001l, "rule_weight");
        log.info("测试结果：{}",strategyRule);
    }
}
