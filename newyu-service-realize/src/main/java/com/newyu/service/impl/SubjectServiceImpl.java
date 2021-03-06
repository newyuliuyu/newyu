package com.newyu.service.impl;

import com.newyu.domain.exam.Subject;
import com.newyu.service.SubjectService;
import com.newyu.service.dao.SubjectDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: SubjectServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-13 下午5:39 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
@Slf4j
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectDao subjectDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSubject(Subject subject) {
        subjectDao.createSubject(subject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSubjects(List<Subject> subjects) {
        subjectDao.createSubjects(subjects);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateSubjectScore(Subject subject) {
        return subjectDao.updateSubjectScore(subject);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSubject(Subject subject) {
        return subjectDao.deleteSubject(subject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSubjectItem(Subject subject) {
        return subjectDao.deleteSubjectItem(subject);
    }

    @Override
    public Subject getSubject(long subjectId) {
        return subjectDao.getSubject(subjectId);
    }

    @Override
    public int updateSubjectWL(Subject subject) {
        return subjectDao.updateSubjectWL(subject);
    }
}
