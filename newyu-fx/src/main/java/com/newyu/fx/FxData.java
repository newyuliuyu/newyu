package com.newyu.fx;

import lombok.*;

/**
 * ClassName: FxData <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-15 下午5:43 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FxData {
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private Object value;
}
