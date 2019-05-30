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
     *
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


    /**
     * 查询考试一个省信息
     *
     * @param examId
     * @param provinceCode
     * @return
     */
    Province getProvice(long examId, String provinceCode);

    /**
     * 查询考试的省数据
     *
     * @param examId
     * @return
     */
    List<Province> queryProvices(long examId);

    /**
     * 查询省下面的地市信息
     *
     * @param examId
     * @param provinceCode
     * @return
     */
    List<City> queryCitysOfProvice(long examId, String provinceCode);

    /**
     * 查询省下面的区县信息
     *
     * @param examId
     * @param provinceCode
     * @return
     */
    List<County> queryCountyOfProvice(long examId, String provinceCode);

    /**
     * 查询省下面的学校信息
     *
     * @param examId
     * @param provinceCode
     * @return
     */
    List<School> querySchoolOfProvice(long examId, String provinceCode);

    /**
     * 获取考试的一个地市信息
     *
     * @param examId
     * @param cityCode
     * @return
     */
    City getCity(long examId, String cityCode);

    /**
     * 查询考试的地市数据
     *
     * @param examId
     * @return
     */
    List<City> queryCitys(long examId);

    /**
     * 获取地市下面的区县信息
     *
     * @param examId
     * @param cityCode
     * @return
     */
    List<County> queryCountyOfCity(long examId, String cityCode);

    /**
     * 获取地市下面的学校信息
     *
     * @param examId
     * @param cityCode
     * @return
     */
    List<School> querySchoolOfCity(long examId, String cityCode);


    /**
     * 获取考试下面一个区县信息
     *
     * @param examId
     * @param countyCode
     * @return
     */
    County getCounty(long examId, String countyCode);

    /**
     * 查询考试区县数据
     *
     * @param examId
     * @return
     */
    List<County> queryCountys(long examId);

    /**
     * 获取区县下面的学校信息
     *
     * @param examId
     * @param countyCode
     * @return
     */
    List<School> querySchoolOfCounty(long examId, String countyCode);

    /**
     * 获取考试下面的一个学校信息
     *
     * @param examId
     * @param schoolCode
     * @return
     */
    School getSchool(long examId, String schoolCode);

    /**
     * 查询考试学校数据
     *
     * @param examId
     * @return
     */
    List<School> querySchools(long examId);

    /**
     * 获取学校下面的班级信息
     *
     * @param examId
     * @param schoolCode
     * @return
     */
    List<Clazz> queryClazzesOfSchool(long examId, String schoolCode);

    /**
     * 获取学校下面的走班班级信息
     *
     * @param examId
     * @param schoolCode
     * @return
     */
    List<TeachClazz> queryTeachClazzesOfSchool(long examId, String schoolCode);

    /**
     * 查询考试班级数据
     *
     * @param examId
     * @return
     */
    List<Clazz> queryClazzes(long examId);

    /**
     * 获取班级信息
     *
     * @param examId
     * @param schoolCode
     * @param clazzCode
     * @return
     */
    Clazz getClazz(long examId, String schoolCode, String clazzCode);

    /**
     * 查询考试走班数据
     *
     * @param examId
     * @return
     */
    List<TeachClazz> queryTeachClazzes(long examId);

    /**
     * 获取走班信息
     *
     * @param examId
     * @param schoolCode
     * @param clazzCode
     * @return
     */
    TeachClazz getTeachClazz(long examId, String subjectName, String schoolCode, String clazzCode);
}
