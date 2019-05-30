package com.newyu.service.dao;

import com.newyu.domain.org.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ExamOrg <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-16 上午10:07 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface ExamOrgDao {

    void saveProinces(@Param("examId") long examId, @Param("orgs") List<Province> provinces);

    void saveCitys(@Param("examId") long examId, @Param("orgs") List<City> citys);

    void saveCountys(@Param("examId") long examId, @Param("orgs") List<County> counties);

    void saveSchools(@Param("examId") long examId, @Param("orgs") List<School> schools);

    void saveClazzes(@Param("examId") long examId, @Param("orgs") List<Clazz> clazzes);

    void saveTeacherClazz(@Param("examId") long examId, @Param("orgs") List<TeachClazz> teachClazzes);

    void saveOrgXSchool(@Param("examId") long examId, @Param("orgs") List<OrgXSchool> OrgXSchools);


    int deleteProinces(@Param("examId") long examId);

    int deleteCitys(@Param("examId") long examId);

    int deleteCountys(@Param("examId") long examId);

    int deleteSchools(@Param("examId") long examId);

    int deleteClazzes(@Param("examId") long examId);

    int deleteTeachers(@Param("examId") long examId);

    int deleteOrgXSchool(@Param("examId") long examId);

}
