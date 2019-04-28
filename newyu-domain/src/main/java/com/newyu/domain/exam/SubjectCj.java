package com.newyu.domain.exam;

import com.google.common.collect.Maps;
import com.newyu.domain.org.TeachClazz;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: SubjectCj <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-12 下午4:15 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"subject", "qk", "score", "teachClazz"})
@Builder
public class SubjectCj {
    private String subject;
    private TeachClazz teachClazz;
    private boolean qk;
    private boolean miss = false;
    private double score;
    private double kgScore;
    private double zgScore;
    private Map<String, ItemCj> itemCjMap = Maps.newHashMap();

    private int qkNum;
    private int missNum;

    public void setItemCjs(List<ItemCj> itemCjs) {
        itemCjMap = itemCjs.stream().collect(Collectors.toMap(x -> x.getItemName(), x -> x));
    }

    public void addItemCj(ItemCj itemCj) {
        itemCjMap.put(itemCj.getItemName(), itemCj);
    }

    public ItemCj queryItemCj(String itemName) {
        return itemCjMap.get(itemName);
    }

}
