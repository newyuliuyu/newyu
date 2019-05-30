package com.newyu.service.impl;

import com.newyu.domain.exam.Subject;
import com.newyu.service.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    @Override
    public void createSubject(Subject subject) {

    }

    @Override
    public int updateSubjectScore(Subject subject) {
        return 0;
    }

    @Override
    public void createSubjects(List<Subject> subjects) {

    }

    @Override
    public int updateSubject(Subject subject) {
        return 0;
    }

    @Override
    public int deleteSubject(Subject subject) {
        return 0;
    }

    @Override
    public int deleteSubjectItem(Subject subject) {
        return 0;
    }

    @Override
    public Subject getSubject(long subjectId) {
        return null;
    }
}
