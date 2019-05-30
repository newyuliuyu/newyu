package com.newyu.service;

import com.newyu.utils.tool.NumberHelper;
import org.junit.Test;

/**
 * ClassName: StringTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-13 上午10:06 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class StringTest {

    @Test
    public void testXiaoshu() throws Exception {
        System.out.println(NumberHelper.isNumeric(""));
        System.out.println(NumberHelper.isNumeric("0"));
        System.out.println(NumberHelper.isNumeric("0.1"));
        System.out.println(NumberHelper.isNumeric(".1"));
        System.out.println(NumberHelper.isNumeric("1."));
        System.out.println(NumberHelper.isNumeric("1a."));
        System.out.println(NumberHelper.isNumeric("1a"));
    }

}
