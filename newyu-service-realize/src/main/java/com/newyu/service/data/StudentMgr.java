package com.newyu.service.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newyu.domain.exam.Student;

import java.util.List;
import java.util.Map;

/**
 * ClassName: StudentMgr <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-9 上午10:28 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class StudentMgr {
    private Map<String, Student> studentMap = Maps.newHashMap();

    public void add(Student student) {
        studentMap.put(student.getZkzh(), student);
    }

    public List<Student> list() {
        return Lists.newArrayList(studentMap.values());
    }
}
