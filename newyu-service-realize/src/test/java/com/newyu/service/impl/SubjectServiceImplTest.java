package com.newyu.service.impl;

import com.google.common.collect.Lists;
import com.newyu.domain.exam.Subject;
import com.newyu.domain.exam.WLType;
import com.newyu.service.AppConfig;
import com.newyu.service.SubjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * ClassName: SubjectServiceImplTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-31 下午3:55 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SubjectServiceImplTest {

    @Autowired
    private SubjectService subjectService;

    @Test
    public void createSubject() throws Exception {
        Subject subject = Subject.builder()
                .examId(1L)
                .id(1L)
                .name("语文")
                .wl(WLType.Wenke)
                .displayOrder(1)
                .fullScore(100)
                .kgFullScore(50)
                .zgFullScore(50)
                .childSubjectNames("语文,英语")
                .parentSubject("总分")
                .childSubject(false)
                .examSubject(true)
                .multiSubject(false)
                .fullSubject(false)
                .build();

        subjectService.createSubject(subject);

    }

    @Test
    public void createSubjects() throws Exception {

        List<Subject> subjects = Lists.newArrayList();
        subjects.add(Subject.builder()
                .examId(1L)
                .id(2L)
                .name("语文2")
                .wl(WLType.Wenke)
                .displayOrder(1)
                .fullScore(100)
                .kgFullScore(50)
                .zgFullScore(50)
                .build());
        subjects.add(Subject.builder()
                .examId(1L)
                .id(3L)
                .name("语文3")
                .wl(WLType.Wenke)
                .displayOrder(1)
                .fullScore(100)
                .kgFullScore(50)
                .zgFullScore(50)
                .build());
        subjects.add(Subject.builder()
                .examId(1L)
                .id(4L)
                .name("语文4")
                .wl(WLType.Wenke)
                .displayOrder(1)
                .fullScore(100)
                .kgFullScore(50)
                .zgFullScore(50)
                .build());


        subjectService.createSubjects(subjects);

    }

    @Test
    public void updateSubjectScore() throws Exception {
        Subject subject = Subject.builder()
                .examId(1L)
                .id(4L)
                .name("语文4")
                .wl(WLType.Wenke)
                .displayOrder(1)
                .fullScore(1000)
                .kgFullScore(500)
                .zgFullScore(500)
                .build();
        subjectService.updateSubjectScore(subject);
    }

    @Test
    public void getSubject() throws Exception{
        Subject subject = subjectService.getSubject(4);
        System.out.println();
    }


}