package com.newyu.fx;

import org.junit.Test;

import java.text.MessageFormat;

/**
 * ClassName: MessageFormatTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-25 下午3:01 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class MessageFormatTest {

    @Test
    public void test() throws Exception {
      String msg =   MessageFormat.format("{0}=={1}", "test1", "test2");
      System.out.println(msg);

    }
}
