package com.newyu.service.dao;

import com.newyu.domain.exam.Subject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: SubjectDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-30 下午4:35 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface SubjectDao {
    void createSubject(@Param("subject") Subject subject);

    void createSubjects(@Param("subjects") List<Subject> subjects);

    int updateSubjectScore(@Param("subject") Subject subject);

    int deleteSubject(@Param("subject") Subject subject);

    int deleteSubjectItem(@Param("subject") Subject subject);

    Subject getSubject(@Param("subjectId") long subjectId);
}
