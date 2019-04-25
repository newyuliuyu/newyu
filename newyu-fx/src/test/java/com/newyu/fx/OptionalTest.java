package com.newyu.fx;

import com.newyu.domain.exam.StudentCj;
import org.junit.Test;

import java.util.Optional;

/**
 * ClassName: OptionalTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-24 下午4:10 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class OptionalTest {
    @Test
    public void test() throws Exception {
        StudentCj studentCj = null;
        Optional<StudentCj> studentCj1 = Optional.ofNullable(studentCj);


        if(studentCj1.isPresent()){
            StudentCj studentCj3 = studentCj1.get();
        }
        System.out.println();

    }
}
