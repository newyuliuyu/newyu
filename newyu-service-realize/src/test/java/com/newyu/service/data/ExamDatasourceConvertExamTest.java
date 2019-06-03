package com.newyu.service.data;

import com.newyu.domain.dto.ExamDatasource;
import com.newyu.domain.exam.Exam;
import com.newyu.service.AppConfig;
import com.newyu.service.ExamService;
import com.newyu.utils.id.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * ClassName: ExamDatasourceConvertExamTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-6-3 上午11:13 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ExamDatasourceConvertExamTest {


    @Autowired
    ExamService examService;
    @Autowired
    IdGenerator idGenerator;

    @Test
    public void createExam() throws Exception {
        ExamDatasource examDatasource = ExamDatasource.builder()
                .examName("考试名字")
                .examType("其他")
                .gradeName("高一")
                .beginDate(new Date())
                .endDate(new Date())
                .sourceId("test1")
                .build();
        ExamDatasourceConvertExam examDatasourceConvertExam = new ExamDatasourceConvertExam(examService, idGenerator);
        Exam exam = examDatasourceConvertExam.convert(examDatasource);
        System.out.println();
    }
    @Test
    public void getExam() throws Exception {
        ExamDatasource examDatasource = ExamDatasource.builder()
                .examName("考试名字123")
                .examType("其他")
                .gradeName("高一")
                .beginDate(new Date())
                .endDate(new Date())
                .sourceId("test1")
                .build();
        ExamDatasourceConvertExam examDatasourceConvertExam = new ExamDatasourceConvertExam(examService, idGenerator);
        Exam exam = examDatasourceConvertExam.convert(examDatasource);
        System.out.println();
    }

}