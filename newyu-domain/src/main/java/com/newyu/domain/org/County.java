package com.newyu.domain.org;

import lombok.*;

/**
 * ClassName: County <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-12 下午4:17 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class County extends Org {
    @Builder
    private County(String code,String name) {
        super(code,name);
    }
}
