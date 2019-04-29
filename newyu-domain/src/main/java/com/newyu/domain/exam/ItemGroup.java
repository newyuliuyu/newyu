package com.newyu.domain.exam;

import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: ItemGroup <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-25 下午4:45 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"propertyName", "name", "fullScore"})
@Builder
public class ItemGroup {
    private Subject subject;
    private String propertyName;
    private String name;
    private List<Item> items = Lists.newArrayList();
    private Double fullScore;

    public Double getFullScore() {
        if (fullScore == null) {
            clacluateFullScore();
        }
        return fullScore;
    }

    private void clacluateFullScore() {
        fullScore = items.stream().reduce(0d, (x, y) -> x + y.getScore(), Double::sum);
    }

    public String getItemNames() {
        List<String> itemNames = items.stream().map(Item::getName).collect(Collectors.toList());
        return String.join(",", itemNames);
    }
}
