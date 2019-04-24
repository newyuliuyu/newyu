package com.newyu.domain.exam;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * ClassName: ItemTypeTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-24 上午11:30 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */

public class ItemTypeTest {


    @Test
    public void test() throws Exception{
        ItemType itemType1 = ItemType.Not_Select;
        ItemType itemType2 = ItemType.Not_Select;

        System.out.println(itemType1==itemType2);
        System.out.println(itemType1.equals(itemType2));
    }
}
