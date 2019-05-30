package com.newyu.service.impl;

import com.newyu.domain.org.*;
import com.newyu.service.ExamOrgService;
import lombok.extern.slf4j.Slf4j;
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
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveProvince(long examId, List<Province> provinces) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCity(long examId, List<City> cities) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCounty(long examId, List<County> counties) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSchool(long examId, List<School> schools) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveClazz(long examId, List<Clazz> clazzes) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTeachClazz(long examId, List<TeachClazz> teachClazzes) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrgXSchool(long examId, List<OrgXSchool> orgXSchools) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteProvice(long examId) {
        return 0;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCity(long examId) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCounty(long examId) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteSchool(long examId) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteClazz(long examId) {
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteTeachClazz(long examId) {
        return 0;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOrgXSchool(long examId) {
        return 0;
    }
}
