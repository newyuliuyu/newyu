package com.newyu.service.data;

import com.newyu.domain.dto.SubjectDatasource;
import com.newyu.service.AppConfig;
import com.newyu.service.ExamXSubjectXItemService;
import com.newyu.service.SubjectService;
import com.newyu.utils.id.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: SubjectDatasourceConvertSubjectTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-6-4 上午11:02 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class SubjectDatasourceConvertSubjectTest {
    @Autowired
    private ExamXSubjectXItemService examXSubjectXItemService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private IdGenerator idGenerator;

    @Test
    public void convert() throws Exception {
        SubjectDatasource subjectDatasource = SubjectDatasource.builder()
                .subjectName("语文")
                .fullScore(100)
                .build();
        SubjectDatasourceConvertSubject convertSubject = new SubjectDatasourceConvertSubject(examXSubjectXItemService, subjectService, idGenerator);
        convertSubject.convert(1L, subjectDatasource);
    }
}