package com.newyu.domain.exam;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: ChoiceItemGroup <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-19 下午2:11 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
public class ChoiceItemGroup {
    /**
     * 选做题规则："从12,13中选1题每题10分"
     */
    private String choiceRule;
    private int choiceNum;
    private double score;
    private String[] itemNames;
    private Subject subject;

    public ChoiceItemGroup(Subject subject, String choiceRule) {
        this.subject = subject;
        this.choiceRule = choiceRule;
        calculate();
    }

    private void calculate() {
//        选做题规则："从12,13中选1题每题10分"
        choiceRule = choiceRule.replace("从", "");
        choiceRule = choiceRule.replace("分", "");
        String[] itemNamesAndRule = choiceRule.split("中选");
        String[] choiceNumAndFullScore = itemNamesAndRule[1].split("题每题");
        itemNames = itemNamesAndRule[0].split(",");
        score = Double.parseDouble(choiceNumAndFullScore[1]);
        choiceNum = Integer.parseInt(choiceNumAndFullScore[0]);
    }

    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        if (itemNames != null) {
            for (String itemName : itemNames) {
                items.add(subject.queryItem(itemName.trim()));
            }
        }
        return items;
    }

}
