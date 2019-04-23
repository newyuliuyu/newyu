package com.newyu.domain.exam;

import lombok.*;

/**
 * ClassName: Exam <br/>
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
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "name"})
@Builder
public class Exam {
    private Long id;
    private String name;
    private ExamState state;

    private GradeName gradeName;
    private int entranceSchoolYear;
    private Semester semester;
    private LearningSegment learningSegment;
    private ExamLevel examLevel;

    private long beginTiem;
    private long endTime;
    private long createTime;
}
