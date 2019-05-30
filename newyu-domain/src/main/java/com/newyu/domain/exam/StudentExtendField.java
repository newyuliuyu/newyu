package com.newyu.domain.exam;

import lombok.*;

/**
 * ClassName: StudentExtendField <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-8 下午5:32 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"code", "name"})
@EqualsAndHashCode(of = {"code"})
@Builder
public class StudentExtendField {
    private String code;
    private String name;
    /**
     * 多个对应文件的名字用户英文的逗号分割
     */
    private String fileName;
}
