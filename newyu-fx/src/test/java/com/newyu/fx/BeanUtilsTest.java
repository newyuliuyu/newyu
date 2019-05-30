package com.newyu.fx;

import com.newyu.domain.exam.Student;
import com.newyu.domain.exam.StudentCj;
import com.newyu.domain.org.Clazz;
import com.newyu.domain.org.School;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

/**
 * ClassName: BeanUtilsTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-25 下午2:32 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class BeanUtilsTest {

    @Test
    public void test01() throws Exception {
        School school = School.builder().code("1").name("学校").build();
        Clazz clazz = Clazz.builder().code("1").name("班级").build();
        clazz.setSchool(school);

        Student student = new Student();
        student.setClazz(clazz);
        student.setSchool(school);
        student.setZkzh("zkzh");
        student.setName("name");

        StudentCj studentCj = new StudentCj();
        studentCj.setStudent(student);

        Object obj1 = PropertyUtils.getProperty(studentCj, "student.school");
        Object obj2 = PropertyUtils.getProperty(studentCj, "student.city");

        System.out.println();
    }
}
