package com.newyu.service.impl;

import com.google.common.collect.Lists;
import com.newyu.domain.exam.Exam;
import com.newyu.service.ExamService;
import com.newyu.service.dao.ExamDao;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public int updateExamState(Exam exam) {
        return examDao.updateExamState(exam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateExamLevel(Exam exam) {
        return examDao.updateExamLevel(exam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateExamWLAndLevel(Exam exam) {
        return examDao.updateExamWLAndLevel(exam);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteExam(Exam exam) {
        return examDao.deleteExam(exam.getId());
    }

    @Override
    public Exam getExam(long examId) {
        return examDao.getExam(examId);
    }

    @Override
    public List<Exam> queryHistoryExam(long exam) {
        if (true) {
            throw new RuntimeException("暂时还没有实现");
        }
        return Lists.newArrayList();
    }

    @Override
    public Exam getExamFromSourceId(String sourceId) {
        return examDao.getExamFromSourceId(sourceId);
    }
}
