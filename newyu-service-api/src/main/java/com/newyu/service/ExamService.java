package com.newyu.service;

import com.newyu.domain.exam.Exam;
import com.newyu.domain.exam.ExamLevel;
import com.newyu.domain.exam.ExamState;

import java.util.List;

/**
 * ClassName: ExamService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-19 上午10:43 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface ExamService {

    /**
     * 创建一次考试
     *
     * @param exam
     * @return
     */
    void createExam(Exam exam);

    /**
     * 修改考试状态
     *
     * @param examId
     * @param examState
     * @return
     */
    int updateExamState(long examId, ExamState examState);

    /**
     * 修改考试级别
     *
     * @param examId
     * @param examLevel
     * @return
     */
    int updateExamLevel(long examId, ExamLevel examLevel);

    /**
     * 删除一次考试
     *
     * @param exam
     * @return
     */
    int deleteExam(Exam exam);

    /**
     * 获取一次考试信息
     *
     * @param examId
     * @return
     */
    Exam getExam(long examId);


    /**
     * 通过考试来源唯一id获取数据
     *
     * @param sourceKey
     * @return
     */
    Exam getExamFromSourceId(String sourceId);

    /**
     * 获取当前考试的历次考试
     *
     * @param exam
     * @return
     */
    List<Exam> queryHistoryExam(long exam);


}
