package com.newyu.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * ClassName: CalendarTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-19 上午9:14 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class CalendarTest {

    @Test
    public void test01() throws Exception {
        Calendar c  = Calendar.getInstance();
        System.out.println(c.getTime());
        System.out.println(new Date());
    }
}
