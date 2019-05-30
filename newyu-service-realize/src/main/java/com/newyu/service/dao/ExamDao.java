package com.newyu.service.dao;

import com.newyu.domain.exam.Exam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * ClassName: ExamDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-22 下午1:37 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface ExamDao {
    int createExam(@Param("exam") Exam exam);

    int updateExamState(@Param("exam") Exam exam);

    int updateExamLevel(@Param("exam") Exam exam);

    int updateExamWLAndLevel(@Param("exam") Exam exam);

    int deleteExam(@Param("examId") long examId);

    Exam getExam(@Param("examId") long examId);

    Exam getExamFromSourceId(@Param("sourceId") String sourceId);
}
