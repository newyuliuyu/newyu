package com.newyu.domain.exam;

import lombok.*;

import java.util.Calendar;
import java.util.Date;

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
    private String examType;

    private Date beginDate;
    private Date endDate;
    private Date createDate;


    public long getBeginTiemstamp() {
        if (beginDate == null) {
            beginDate = new Date();
        }
        return beginDate.getTime();
    }

    public void setBeginTiemstamp(long beginTiemstamp) {
        this.beginDate = new Date(beginTiemstamp);
    }

    public long getEndTimestamp() {
        if (endDate == null) {
            endDate = new Date();
            Calendar calendar = Calendar.getInstance();
        }
        return endDate.getTime();
    }

    public void setEndTimestamp(long endTimestamp) {
        this.endDate = new Date(endTimestamp);
    }

    public long getCreateTimestamp() {
        if (createDate == null) {
            createDate = new Date();
        }
        return createDate.getTime();
    }

    public void setCreateTimestamp(long createTimestamp) {
        this.createDate = new Date(createTimestamp);
    }
}
