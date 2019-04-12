package com.newyu.domain.exam;

import com.newyu.domain.org.Clazz;
import com.newyu.domain.org.School;
import lombok.*;

import java.util.List;

/**
 * ClassName: StudentCj <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-12 下午4:14 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"name", "zkzh", "ownId", "subjectCjs", "school", "clazz"})
@Builder
public class StudentCj {
    private String name;
    private String zkzh;
    private String code;
    private String ownId;
    private School school;
    private Clazz clazz;
    private List<SubjectCj> subjectCjs;
}
