package com.leesee.domain.strategy.model.vo;

import com.leesee.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;
import com.leesee.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @Title: StrategyAwardRuleModelVO
 * @Author leesee
 * @Package com.leesee.domain.strategy.model.vo
 * @Date 2024/10/9 1:54
 * @description: 抽奖策略规则值对象，仅限于从db中查询对象
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyAwardRuleModelVO {
    private String ruleModels;

    public String[] raffleCenterRuleModelList(){
        return Arrays.stream(ruleModels.split(Constants.SPLIT))
                .filter(DefaultLogicFactory.LogicModel::isCenter).toArray(String[]::new);
    }
}
