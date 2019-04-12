package com.newyu.domain.org;

import lombok.*;

/**
 * ClassName: Org <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-12 下午4:16 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"code"})
@ToString(of = {"code", "name"})
@Builder(builderMethodName = "orgBuilder")
public class Org {
    private String code;
    private String name;
}
