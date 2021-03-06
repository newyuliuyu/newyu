package com.newyu.service;

import com.newyu.domain.exam.Subject;
import com.newyu.domain.fx.SubjectDataVersion;

import java.util.List;

/**
 * ClassName: ExamXSubjectService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-19 上午11:06 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface ExamXSubjectXItemService {

    /**
     * 查询一次考试的所有科目带有小题信息
     *
     * @param examId
     * @return
     */
    List<Subject> querySubjectOfExam(long examId);

    /**
     * 查询一个科目带有小题信息
     * @param subjectId
     * @return
     */
    Subject querySubject(long subjectId);


    /**
     * 获取考试科目的数据版本
     *
     * @param examId
     * @return
     */
    List<SubjectDataVersion> querySubjectDataVersion(long examId);

    /**
     * 获取科目数据
     * @param examId
     * @param subjectName
     * @return
     */
    Subject getSubject(long examId,String subjectName);
}
