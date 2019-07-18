package com.newyu.fx;

import com.newyu.domain.exam.Exam;
import com.newyu.domain.exam.Subject;
import com.newyu.service.ExamService;
import com.newyu.service.SubjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * ClassName: AppConfigTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-6-14 上午10:04 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class AppConfigTest {

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ExamService examService;

    @Test
    public void test() throws Exception {
        Subject subject = subjectService.getSubject(1138741963286290432L);
        System.out.println();
    }

    @Test
    public void getExam() throws Exception {
        Exam exam = examService.getExam(1135447716571254784L);
        System.out.println();
    }

}
