package com.newyu.service.impl;

import com.newyu.domain.org.*;
import com.newyu.service.ExamOrgService;
import com.newyu.service.dao.ExamOrgDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: ExamOrgImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-15 下午3:53 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
@Slf4j
public class ExamOrgImpl implements ExamOrgService {
    @Autowired
    private ExamOrgDao examOrgDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProvince(long examId, List<Province> provinces) {
        examOrgDao.saveProinces(examId, provinces);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCity(long examId, List<City> cities) {
        examOrgDao.saveCitys(examId, cities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCounty(long examId, List<County> counties) {
        examOrgDao.saveCountys(examId, counties);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSchool(long examId, List<School> schools) {
        examOrgDao.saveSchools(examId, schools);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveClazz(long examId, List<Clazz> clazzes) {
        examOrgDao.saveClazzes(examId, clazzes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTeachClazz(long examId, List<TeachClazz> teachClazzes) {
        examOrgDao.saveTeacherClazz(examId, teachClazzes);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrgXSchool(long examId, List<OrgXSchool> orgXSchools) {
        examOrgDao.saveOrgXSchool(examId, orgXSchools);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteProvice(long examId) {
        return examOrgDao.deleteProinces(examId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCity(long examId) {
        return examOrgDao.deleteCitys(examId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCounty(long examId) {
        return examOrgDao.deleteCountys(examId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSchool(long examId) {
        return examOrgDao.deleteSchools(examId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteClazz(long examId) {
        return examOrgDao.deleteClazzes(examId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteTeachClazz(long examId) {
        return examOrgDao.deleteTeachers(examId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOrgXSchool(long examId) {
        return examOrgDao.deleteOrgXSchool(examId);
    }

    @Override
    public Province getProvice(long examId, String provinceCode) {
        return examOrgDao.getProvice(examId, provinceCode);
    }

    @Override
    public List<Province> queryProvices(long examId) {
        return examOrgDao.queryProvices(examId);
    }

    @Override
    public List<City> queryCitysOfProvice(long examId, String provinceCode) {
        return examOrgDao.queryCitysOfProvice(examId, provinceCode);
    }

    @Override
    public List<County> queryCountyOfProvice(long examId, String provinceCode) {
        return examOrgDao.queryCountyOfProvice(examId, provinceCode);
    }

    @Override
    public List<School> querySchoolOfProvice(long examId, String provinceCode) {
        return examOrgDao.querySchoolOfProvice(examId, provinceCode);
    }

    @Override
    public City getCity(long examId, String cityCode) {
        return examOrgDao.getCity(examId, cityCode);
    }

    @Override
    public List<City> queryCitys(long examId) {
        return examOrgDao.queryCitys(examId);
    }

    @Override
    public List<County> queryCountyOfCity(long examId, String cityCode) {
        return examOrgDao.queryCountyOfCity(examId, cityCode);
    }

    @Override
    public List<School> querySchoolOfCity(long examId, String cityCode) {
        return examOrgDao.querySchoolOfCity(examId, cityCode);
    }

    @Override
    public County getCounty(long examId, String countyCode) {
        return examOrgDao.getCounty(examId, countyCode);
    }

    @Override
    public List<County> queryCountys(long examId) {
        return examOrgDao.queryCountys(examId);
    }

    @Override
    public List<School> querySchoolOfCounty(long examId, String countyCode) {
        return examOrgDao.querySchoolOfCounty(examId, countyCode);
    }

    @Override
    public School getSchool(long examId, String schoolCode) {
        return examOrgDao.getSchool(examId, schoolCode);
    }

    @Override
    public List<School> querySchools(long examId) {
        return examOrgDao.querySchools(examId);
    }

    @Override
    public List<Clazz> queryClazzesOfSchool(long examId, String schoolCode) {
        return examOrgDao.queryClazzesOfSchool(examId, schoolCode);
    }

    @Override
    public List<TeachClazz> queryTeachClazzesOfSchool(long examId, String schoolCode) {
        return examOrgDao.queryTeachClazzesOfSchool(examId, schoolCode);
    }

    @Override
    public List<Clazz> queryClazzes(long examId) {
        return examOrgDao.queryClazzes(examId);
    }

    @Override
    public Clazz getClazz(long examId, String schoolCode, String clazzCode) {
        return examOrgDao.getClazz(examId, schoolCode, clazzCode);
    }

    @Override
    public List<TeachClazz> queryTeachClazzes(long examId) {
        return examOrgDao.queryTeachClazzes(examId);
    }

    @Override
    public TeachClazz getTeachClazz(long examId, String subjectName, String schoolCode, String clazzCode) {
        return examOrgDao.getTeachClazz(examId, subjectName, schoolCode, clazzCode);
    }
}
