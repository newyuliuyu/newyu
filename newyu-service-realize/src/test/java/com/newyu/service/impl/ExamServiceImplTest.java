package com.newyu.service.impl;

import com.newyu.domain.exam.*;
import com.newyu.service.AppConfig;
import com.newyu.utils.id.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @Autowired
    private IdGenerator idGenerator;

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//    @Capturing
//    private ExamDao examDao;

    @Test
    public void test() throws Exception{
        Date date = new Date(1559284191000L);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.format(date));
    }

    @Test
    public void createExam() throws Exception {

        Exam exam = Exam.builder()
                .id(1L)
                .name("测试考试保存数据")
                .state(ExamState.create)
                .gradeName(GradeName.Gaoyi)
                .entranceSchoolYear(2001)
                .semester(Semester.LAST_Semester)
                .learningSegment(LearningSegment.High_School)
                .examLevel(ExamLevel.School_Exam)
                .wl(0)
                .examType("期中期末考")
                .beginDate(new Date())
                .endDate(new Date())
                .createDate(new Date())
                .sourceId("123")
                .build();
        examService.createExam(exam);
    }

    @Test
    public void udpateExamStatus()throws Exception{
        Exam exam = Exam.builder()
                .id(1L)
                .state(ExamState.analyzeWait)
                .examLevel(ExamLevel.County_Exam)
                .wl(1)
                .build();
        examService.updateExamState(exam);
        examService.updateExamLevel(exam);
        examService.updateExamWLAndLevel(exam);
    }
    @Test
    public void getExam() throws Exception {

        Exam exam = examService.getExam(1L);

        Exam exam1=examService.getExamFromSourceId("123");

        System.out.println();
    }
}
