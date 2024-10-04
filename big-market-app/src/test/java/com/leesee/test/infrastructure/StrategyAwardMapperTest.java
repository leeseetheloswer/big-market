package com.leesee.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.leesee.infrastructure.persistent.dao.IAwardMapper;
import com.leesee.infrastructure.persistent.dao.IStrategyAwardMapper;
import com.leesee.infrastructure.persistent.po.Award;
import com.leesee.infrastructure.persistent.po.StrategyAward;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Title: StrategyAwardMapperTest
 * @Author leesee
 * @Package com.leesee.test.infrastructure
 * @Date 2024/10/4 15:52
 * @description: 策略商品mapper测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyAwardMapperTest {
    @Resource
    private IStrategyAwardMapper strategyAwardMapper;
    @Test
    public void testQueryStrategyAwardList(){
        List<StrategyAward> strategyAwards = strategyAwardMapper.queryStrategyAwardList();
        log.info("测试结果：{}", JSON.toJSONString(strategyAwards));
    }
}
