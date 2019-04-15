package com.newyu.domain.exam;

import lombok.*;

/**
 * ClassName: ItemCj <br/>
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
@ToString(of = {"itemId", "itemName", "score", "options"})
@Builder
public class ItemCj {
    private int itemId;
    private String itemName;
    private double score;
    private String options;
}
