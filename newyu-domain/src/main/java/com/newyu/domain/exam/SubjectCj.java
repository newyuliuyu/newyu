package com.newyu.domain.exam;

import com.google.common.collect.Maps;
import com.newyu.domain.org.TeachClazz;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    @Builder.Default
    private boolean qk = false;
    @Builder.Default
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
        Optional<ItemCj> itemCj = Optional.ofNullable(itemCjMap.get(itemName));
        return itemCj.orElse(ItemCj.builder().itemName(itemName).score(0d).selected("").build());
    }

    public double getItemGroupScore(ItemGroup itemGroup) {
        List<Item> items = itemGroup.getItems();
        double sumScore = 0d;
        for (Item item : items) {
            sumScore += queryItemCj(item.getName()).getScore();
        }
        return sumScore;
    }
}
