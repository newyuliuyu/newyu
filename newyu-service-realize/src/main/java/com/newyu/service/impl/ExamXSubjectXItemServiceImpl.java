package com.newyu.service.impl;

import com.newyu.domain.exam.Subject;
import com.newyu.domain.fx.SubjectDataVersion;
import com.newyu.service.ExamXSubjectXItemService;
import com.newyu.service.dao.SubjectDao;
import com.newyu.service.dao.SubjectDataVersionDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private SubjectDataVersionDao subjectDataVersionDao;

    @Override
    public List<Subject> querySubjectOfExam(long examId) {
        return subjectDao.querySubjects(examId);
    }


    @Override
    public Subject querySubject(long subjectId) {
        return subjectDao.querySubject(subjectId);
    }

    @Override
    public List<SubjectDataVersion> querySubjectDataVersion(long examId) {
        return subjectDataVersionDao.list(examId);
    }

    @Override
    public Subject getSubject(long examId, String subjectName) {
        return subjectDao.getSubjectForName(examId, subjectName);
    }
}
