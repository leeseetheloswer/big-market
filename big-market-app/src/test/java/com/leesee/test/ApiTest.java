package com.leesee.test;

import com.leesee.infrastructure.persistent.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMap;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private IRedisService redisService;
    @Test
    public void test() {
        RMap<Object, Object> testMap = redisService.getMap("test");
        testMap.put("1","101");
        testMap.put("2","102");
        testMap.put("3","103");
        testMap.put("4","104");
        log.info("测试结果1:{}",testMap.get("1").toString());
        log.info("测试完成");
    }

}
