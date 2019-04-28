package com.newyu.fx;

import com.newyu.domain.exam.Exam;
import com.newyu.domain.exam.Subject;
import com.newyu.domain.fx.SubjectDataVersion;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ClassName: ExamInfoMgr <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-28 上午10:51 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
public class ExamBaseInfoMgr {
    private Exam exam;
    private List<Subject> subjects;
    private List<SubjectDataVersion> subjectDataVersions;
}
