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


    Province getProvice(@Param("examId") long examId,
                        @Param("provinceCode") String provinceCode);

    List<Province> queryProvices(@Param("examId") long examId);

    List<City> queryCitysOfProvice(@Param("examId") long examId,
                                   @Param("provinceCode") String provinceCode);

    List<County> queryCountyOfProvice(@Param("examId") long examId,
                                      @Param("provinceCode") String provinceCode);

    List<School> querySchoolOfProvice(@Param("examId") long examId,
                                      @Param("provinceCode") String provinceCode);

    City getCity(@Param("examId") long examId,
                 @Param("cityCode") String cityCode);

    List<City> queryCitys(@Param("examId") long examId);

    List<County> queryCountyOfCity(@Param("examId") long examId,
                                   @Param("cityCode") String cityCode);

    List<School> querySchoolOfCity(@Param("examId") long examId,
                                   @Param("cityCode") String cityCode);


    County getCounty(@Param("examId") long examId,
                     @Param("countyCode") String countyCode);

    List<County> queryCountys(@Param("examId") long examId);

    List<School> querySchoolOfCounty(@Param("examId") long examId,
                                     @Param("countyCode") String countyCode);


    School getSchool(@Param("examId") long examId,
                     @Param("schoolCode") String schoolCode);

    List<School> querySchools(@Param("examId") long examId);

    List<Clazz> queryClazzesOfSchool(@Param("examId") long examId,
                                     @Param("schoolCode") String schoolCode);

    List<TeachClazz> queryTeachClazzesOfSchool(@Param("examId") long examId,
                                               @Param("schoolCode") String schoolCode);

    List<Clazz> queryClazzes(@Param("examId") long examId);

    Clazz getClazz(@Param("examId") long examId,
                     @Param("schoolCode") String schoolCode,
                     @Param("clazzCode") String clazzCode);

    List<TeachClazz> queryTeachClazzes(@Param("examId") long examId);

    TeachClazz getTeachClazz(@Param("examId") long examId,
                               @Param("subjectName") String subjectName,
                               @Param("schoolCode") String schoolCode,
                               @Param("clazzCode") String clazzCode);
}
