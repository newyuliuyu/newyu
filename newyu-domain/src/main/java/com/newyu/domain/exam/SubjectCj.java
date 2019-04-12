package com.newyu.domain.exam;

import com.newyu.domain.org.TeachClazz;
import lombok.*;

import java.util.List;

/**
 * ClassName: SubjectCj <br/>
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
@ToString(of = {"subject", "qk", "score", "teachClazz"})
@Builder
public class SubjectCj {
    private Subject subject;
    private TeachClazz teachClazz;
    private boolean qk;
    private double score;
    private double kgScore;
    private double zgScore;
    private List<ItemCj> itemCjs;
}
