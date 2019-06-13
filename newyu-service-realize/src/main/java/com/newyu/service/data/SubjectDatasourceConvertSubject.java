package com.newyu.service.data;

import com.newyu.domain.dto.SubjectDatasource;
import com.newyu.domain.exam.Subject;
import com.newyu.service.ExamXSubjectXItemService;
import com.newyu.service.SubjectService;
import com.newyu.utils.id.IdGenerator;

/**
 * ClassName: SubjectSourceDataConvertSubject <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-10 下午5:40 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class SubjectDatasourceConvertSubject {
    private SubjectService subjectService;
    private IdGenerator idGenerator;
    private ExamXSubjectXItemService examXSubjectXItemService;

    public SubjectDatasourceConvertSubject(ExamXSubjectXItemService examXSubjectXItemService,
                                           SubjectService subjectService,
                                           IdGenerator idGenerator) {
        this.subjectService = subjectService;
        this.idGenerator = idGenerator;
        this.examXSubjectXItemService = examXSubjectXItemService;
    }

    public Subject convert(long examId, SubjectDatasource subjectDatasource) {
        Subject oldSubject= subjectDatasource.toSubject();
        Subject subject = examXSubjectXItemService.getSubject(examId, oldSubject.getName());
        if (subject == null) {
            subject = oldSubject;
            subject.setExamId(examId);
            subject.setId(idGenerator.nextId());
            subjectService.createSubject(subject);
        }
        return subject;
    }

}
