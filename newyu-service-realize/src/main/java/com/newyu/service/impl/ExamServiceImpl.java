package com.newyu.service.impl;

import com.newyu.domain.exam.Exam;
import com.newyu.domain.exam.ExamState;
import com.newyu.service.ExamService;
import com.newyu.service.dao.ExamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void createExam(Exam exam) {
        examDao.createExam(exam);
        System.out.println("this is exam service impl class");
    }

    @Override
    public int updateExam(long examId, ExamState examState) {
        return 0;
    }

    @Override
    public int deleteExam(Exam exam) {
        return 0;
    }

    @Override
    public Exam getExam(long examId) {
        return null;
    }

    @Override
    public List<Exam> queryHistoryExam(long exam) {
        return null;
    }
}
