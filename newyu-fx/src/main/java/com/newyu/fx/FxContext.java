package com.newyu.fx;

import com.newyu.domain.exam.Exam;
import com.newyu.domain.exam.StudentCj;
import com.newyu.domain.exam.Subject;

import java.util.List;
import java.util.function.Predicate;

/**
 * ClassName: FxContext <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-12 下午3:47 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface FxContext {

    /**
     * 获取考试
     *
     * @return
     */
    Exam getExam();

    /**
     * 获取考试的科目
     *
     * @return
     */
    List<Subject> getSubject();

    /**
     * 获取科目成绩换算规则
     *
     * @param subject
     * @return
     */
    SubjectCjConversion getSubjectCjConversions(Subject subject);

    /**
     * 获取学生信息过滤条件
     *
     * @return
     */
    Predicate<StudentCj> getPredicateOfStudent();

    /**
     * 获取科目成绩上的过滤条件
     *
     * @param subject
     * @return
     */
    Predicate<StudentCj> getPredicateOfSubjectCj(Subject subject);
}
