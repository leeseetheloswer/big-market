package com.leesee.infrastructure.persistent.dao;

import com.leesee.infrastructure.persistent.po.RuleTree;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author leesee
 * @description 规则树表mapper
 * @create 2024-10-20
 */
@Mapper
public interface IRuleTreeMapper {

    RuleTree queryRuleTreeByTreeId(String treeId);

}
