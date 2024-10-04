package com.leesee.infrastructure.persistent.dao;

import com.leesee.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Title: IAwardMapper
 * @Author leesee
 * @Package com.leesee.infrastructure.persistent.dao
 * @Date 2024/10/4 15:08
 * @description: 奖品mapper
 */
@Mapper
public interface IAwardMapper {
    List<Award> queryAwardList();
}
