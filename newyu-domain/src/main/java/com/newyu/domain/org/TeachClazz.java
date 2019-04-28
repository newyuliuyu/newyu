package com.newyu.domain.org;

import lombok.*;

/**
 * ClassName: TeachClazz <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-12 下午5:30 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"subjectName"}, callSuper = true)
@ToString(of = {"subjectName"}, callSuper = true)
public class TeachClazz extends Clazz {
    private String subjectName;

    @Builder(builderMethodName = "teachClazzBuilder")
    private TeachClazz(String schoolCode, String schoolName, String code, String name, String subjectName, String group) {
        super(schoolCode, schoolName, code, name, 0, group);
        this.subjectName = subjectName;
    }
}
