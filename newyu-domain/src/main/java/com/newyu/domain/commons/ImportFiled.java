package com.newyu.domain.commons;

import com.newyu.domain.exam.Subject;
import lombok.*;

/**
 * ClassName: ImportFiled <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-15 下午3:22 <br/>
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
@ToString(of = {"code", "name", "hasValue"})
public class ImportFiled {
    @Builder.Default
    private Subject subject = Subject.builder().id(0L).build();
    private String code;
    private String name;
    private boolean hasValue;
}
