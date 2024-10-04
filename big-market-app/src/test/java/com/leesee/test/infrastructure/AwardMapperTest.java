package com.leesee.test.infrastructure;

import com.alibaba.fastjson.JSON;
import com.leesee.infrastructure.persistent.dao.IAwardMapper;
import com.leesee.infrastructure.persistent.po.Award;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Title: AwardMapperTest
 * @Author leesee
 * @Package com.leesee.test.infrastructure
 * @Date 2024/10/4 15:34
 * @description: 奖品mapper测试
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardMapperTest {
    @Resource
    private IAwardMapper awardMapper;
    @Test
    public void testQueryAwardList(){
        List<Award> awards = awardMapper.queryAwardList();
        log.info("测试结果：{}", JSON.toJSONString(awards));
    }



}
