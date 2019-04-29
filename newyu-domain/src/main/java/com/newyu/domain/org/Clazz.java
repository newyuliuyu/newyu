package com.newyu.domain.org;

import com.newyu.domain.fx.FxFiled;
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
@FxFiled(of = {"code", "name", "school.name", "school.code"})
public class Clazz extends Org {
    private School school;
    private int wl;
    private String group;

    @Builder
    protected Clazz(String schoolCode, String schoolName, String code, String name, int wl, String group) {
        super(code, name);
        this.school = School.builder().code(schoolCode).name(schoolName).build();
        this.wl = wl;
        this.group = group;
    }
}
