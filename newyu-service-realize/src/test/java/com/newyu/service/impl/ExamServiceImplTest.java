package com.newyu.service.impl;

import com.newyu.service.AppConfig;
import com.newyu.service.dao.ExamDao;
import mockit.Capturing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: ExamServiceImplTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-22 上午11:24 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ExamServiceImplTest {

    @Autowired
    private ExamServiceImpl examService;

    @Capturing
    private ExamDao examDao;

    @Test
    public void createExam() {
//        Exam exam = Exam.builder().id(1L).build();
//        new Expectations() {
//            {
//                examDao.getExam(1);
//                result = exam;
//            }
//        };
//        examService.createExam(null);
//        Exam exam1 = examDao.getExam(1);
//        System.out.println();
    }
}
