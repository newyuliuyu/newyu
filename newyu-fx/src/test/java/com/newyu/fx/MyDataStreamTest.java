package com.newyu.fx;

import org.junit.Test;

/**
 * ClassName: MyDataStreamTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-20 上午10:30 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class MyDataStreamTest {

    @Test
    public void test() throws Exception {
        MyDataStream<Double> my = new MyDataStream();
        double sum = my.stream(new MyDataset()).parallel().reduce(0d,(x,y)->x+y);
        System.out.println();
    }
}
