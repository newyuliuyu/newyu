package com.newyu.service.impl;

import com.newyu.domain.exam.Subject;
import com.newyu.domain.fx.SubjectDataVersion;
import com.newyu.service.ExamXSubjectXItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ExamXSubjectXItemServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-13 下午5:37 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
@Service
public class ExamXSubjectXItemServiceImpl implements ExamXSubjectXItemService {
    @Override
    public List<Subject> querySubjectOfExam(long examId) {
        return null;
    }

    @Override
    public List<Subject> querySubjectOfExamHasItem(long examId) {
        return null;
    }

    @Override
    public List<SubjectDataVersion> querySubjectDataVersion(long examId) {
        return null;
    }

    @Override
    public Subject getSubject(long examId, String subjectName) {
        return null;
    }
}
