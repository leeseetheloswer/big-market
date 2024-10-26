package com.leesee.infrastructure.persistent.dao;

import com.leesee.infrastructure.persistent.po.RuleTreeNodeLine;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author leesee
 * @description 规则树节点连线表mapper
 * @create 2024-10-20
 */
@Mapper
public interface IRuleTreeNodeLineMapper {

    List<RuleTreeNodeLine> queryRuleTreeNodeLineListByTreeId(String treeId);

}
