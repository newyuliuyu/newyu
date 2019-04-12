package com.newyu.domain.org;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * ClassName: ClazzTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-12 下午4:40 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class ClazzTest {

    @Test
    public void createTest() throws Exception {
        Clazz clazz = Clazz.builder().schoolName("schoolName").schoolCode("schoolCode").code("code").name("name").build();

    }

}
