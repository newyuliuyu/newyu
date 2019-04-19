package com.newyu.service;

import com.newyu.domain.exam.Subject;

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
     * 查询一次考试的所有科目
     *
     * @param examId
     * @return
     */
    List<Subject> querySubjectOfExam(long examId);

    /**
     * 查询科目带有科目题目数据
     *
     * @param examId
     * @return
     */
    List<Subject> querySubjectOfExamHasItem(long examId);


}
