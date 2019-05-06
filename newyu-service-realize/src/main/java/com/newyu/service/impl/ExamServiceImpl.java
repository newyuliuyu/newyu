package com.newyu.service.impl;

import com.newyu.domain.exam.Exam;
import com.newyu.domain.exam.ExamLevel;
import com.newyu.domain.exam.ExamState;
import com.newyu.service.ExamService;
import com.newyu.service.dao.ExamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: ExamServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-22 上午11:22 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDao examDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createExam(Exam exam) {
        examDao.createExam(exam);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateExamState(long examId, ExamState examState) {
        Exam exam = Exam.builder().id(examId).state(examState).build();
        return examDao.updateExamState(exam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateExamLevel(long examId, ExamLevel examLevel) {
        Exam exam = Exam.builder().id(examId).examLevel(examLevel).build();
        return examDao.updateExamLevel(exam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteExam(Exam exam) {
        return examDao.deleteExam(exam.getId());
    }

    @Override
    public Exam getExam(long examId) {
        return null;
    }

    @Override
    public List<Exam> queryHistoryExam(long exam) {
        return null;
    }

    @Override
    public Exam getExamFromSourceId(String sourceId) {
        return null;
    }
}
