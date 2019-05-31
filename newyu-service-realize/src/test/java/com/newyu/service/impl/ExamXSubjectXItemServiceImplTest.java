package com.newyu.service.impl;

import com.newyu.domain.exam.Subject;
import com.newyu.service.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: ExamXSubjectXItemServiceImplTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-31 下午4:17 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class ExamXSubjectXItemServiceImplTest {

    @Autowired
    private ExamXSubjectXItemServiceImpl examXSubjectXItemService;


    @Test
    public void querySubjectOfExam() throws Exception{
        List<Subject> subjects = examXSubjectXItemService.querySubjectOfExam(1L);

        Subject subject = new Subject();

        System.out.println();
    }
    @Test
    public void querySubject() throws Exception{
        Subject subject = examXSubjectXItemService.querySubject(4L);

        System.out.println();
    }

}