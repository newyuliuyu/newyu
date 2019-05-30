package com.newyu.service;

import com.newyu.domain.org.*;

import java.util.List;

/**
 * ClassName: ExamOrg <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-15 下午3:40 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface ExamOrgService {

    /**
     * 保存考试的省数据
     *
     * @param examId
     * @param provinces
     */
    void saveProvince(long examId, List<Province> provinces);

    /**
     * 保存考试的区县数据
     *
     * @param examId
     * @param cities
     */
    void saveCity(long examId, List<City> cities);

    /**
     * 保存考试区县数据
     *
     * @param examId
     * @param counties
     */
    void saveCounty(long examId, List<County> counties);

    /**
     * 保存考试的学校数据
     *
     * @param examId
     * @param schools
     */
    void saveSchool(long examId, List<School> schools);

    /**
     * 保存考试的班级数据
     *
     * @param examId
     * @param clazzes
     */
    void saveClazz(long examId, List<Clazz> clazzes);

    /**
     * 保存考试的任教数据
     *
     * @param examId
     * @param teachClazzes
     */
    void saveTeachClazz(long examId, List<TeachClazz> teachClazzes);

    /**
     * 保存结构与学校的关系
     * @param examId
     * @param orgXSchools
     */
    void saveOrgXSchool(long examId, List<OrgXSchool> orgXSchools);

    /**
     * 删除考试的省信息
     *
     * @param examId
     * @return
     */
    int deleteProvice(long examId);

    /**
     * 删除考试地市信息
     *
     * @param examId
     * @return
     */
    int deleteCity(long examId);

    /**
     * 删除考试的区县信息
     *
     * @param examId
     * @return
     */
    int deleteCounty(long examId);

    /**
     * 删除考试的学校信息
     *
     * @param examId
     * @return
     */
    int deleteSchool(long examId);

    /**
     * 删除考试的班级信息
     *
     * @param examId
     * @return
     */
    int deleteClazz(long examId);

    /**
     * 删除考试的教学班信息
     *
     * @param examId
     * @return
     */
    int deleteTeachClazz(long examId);

    /**
     * 删除学校与结构的关系
     *
     * @param examId
     * @return
     */
    int deleteOrgXSchool(long examId);
}
