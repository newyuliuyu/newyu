package com.newyu.service.dao;

import com.newyu.domain.fx.SubjectDataVersion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: SubjectDataVersionDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-16 下午2:13 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface SubjectDataVersionDao {

    void createSubjectDataVersion(@Param("version") SubjectDataVersion version);

    int update(@Param("version") SubjectDataVersion version);

    int delete(@Param("version") SubjectDataVersion version);

    SubjectDataVersion get(@Param("subjectId") long subjectId);

    List<SubjectDataVersion> list(@Param("examId") long examId);

}
