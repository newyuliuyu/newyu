package com.newyu.domain.org;

import lombok.*;

/**
 * ClassName: Clazz <br/>
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
@EqualsAndHashCode(of = {"school"}, callSuper = true)
@ToString(of = {"school"}, callSuper = true)
public class Clazz extends Org {
    private School school;

    @Builder
    protected Clazz(String schoolCode, String schoolName, String code, String name) {
        super(code, name);
        this.school = School.builder().code(schoolCode).name(schoolName).build();
    }
}