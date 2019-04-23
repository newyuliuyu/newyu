package com.newyu.service.dao;

import com.newyu.domain.exam.Exam;
import com.newyu.domain.exam.ExamState;
import com.newyu.service.AppConfig;
import com.newyu.utils.id.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: ExamDaoTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-23 上午9:36 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ExamDaoTest {

    @Autowired
    ExamDao examDao;

    @Autowired
    IdGenerator idGenerator;

    @Test
    public void createExam() throws Exception {
        Exam exam = Exam.builder().id(idGenerator.nextId()).name("test").state(ExamState.analyzeWait).build();
        examDao.createExam(exam);
    }

    @Test
    public void getExam() throws Exception {
        Exam exam = examDao.getExam(1120506873968267264L);
        System.out.println();
    }
}
