package com.newyu.fx;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * ClassName: GroupValueTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-28 上午10:31 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class GroupValueTest {

    @Test
    public void test01() throws Exception{
        GroupValue groupValue1 = new GroupValue();
        groupValue1.addValue("1",1);

        GroupValue groupValue2 = new GroupValue();
        groupValue2.setParent(groupValue1);
        groupValue2.addValue("2",2);


        GroupValue groupValue4 = new GroupValue();
        groupValue4.addValue("1",1);

        GroupValue groupValue3 = new GroupValue();
        groupValue3.setParent(groupValue4);
        groupValue3.addValue("2",2);

        System.out.println(groupValue3.equals(groupValue2));

        System.out.println();
    }

}