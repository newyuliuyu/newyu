package com.newyu.service.dao;

import com.newyu.domain.exam.StudentExtendField;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * ClassName: StudentExtendFieldDao <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-30 下午4:59 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Repository
public interface StudentExtendFieldDao {
    void create(@Param("filed") StudentExtendField field);

    Set<StudentExtendField> getStudentExtendFields();
}
