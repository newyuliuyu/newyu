package com.newyu.service.impl;

import com.google.common.collect.Sets;
import com.newyu.domain.exam.StudentExtendField;
import com.newyu.service.StudentExtendFieldService;
import lombok.extern.slf4j.Slf4j;
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
    @Override
    public Set<StudentExtendField> getStudentExtendFields() {
        return Sets.newHashSet();
    }
}
