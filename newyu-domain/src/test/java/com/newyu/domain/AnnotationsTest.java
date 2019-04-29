package com.newyu.domain;

import com.newyu.domain.exam.Item;
import com.newyu.domain.fx.FxFiled;
import com.newyu.domain.org.Clazz;
import com.newyu.domain.org.School;
import org.junit.Test;

import java.lang.annotation.Annotation;

/**
 * ClassName: AnnotationsTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-28 下午5:26 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class AnnotationsTest {

    @Test
    public void test() throws Exception {
        School school = School.builder().code("test").name("test").build();
        Clazz clazz = Clazz.builder().code("test1").name("test2").build();
        clazz.setSchool(school);

        FxFiled fxFiled = school.getClass().getAnnotation(FxFiled.class);
        String[] fileds = fxFiled.of();
        Annotation[] annotations = school.getClass().getAnnotations();
        System.out.println();

    }
    @Test
    public void test2() throws Exception {
        Item item = Item.builder().id(1).build();


        System.out.println();

    }
}
