package com.newyu.service.impl;

import com.newyu.domain.exam.StudentExtendField;
import com.newyu.service.StudentExtendFieldService;
import com.newyu.service.dao.StudentExtendFieldDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * ClassName: StudentExtendFieldServiceImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-13 下午5:24 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Service
@Slf4j
public class StudentExtendFieldServiceImpl implements StudentExtendFieldService {
    @Autowired
    private StudentExtendFieldDao studentExtendFieldDao;

    @Override
    public Set<StudentExtendField> getStudentExtendFields() {
        return studentExtendFieldDao.getStudentExtendFields();
    }
}
