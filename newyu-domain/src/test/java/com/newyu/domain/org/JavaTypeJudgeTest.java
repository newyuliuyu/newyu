package com.newyu.domain.org;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * ClassName: JavaTypeJudgeTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-16 下午1:59 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class JavaTypeJudgeTest {

    @Test
    public void test() throws Exception {

        Map<String,Object> dataMap = Maps.newHashMap();
        dataMap.put("test1",1);
        dataMap.put("test2",1.1f);
        dataMap.put("test3",new School());

        if(dataMap.get("test1") instanceof  Integer){
            System.out.println();
        }
        if(dataMap.get("test2") instanceof  Float){
            System.out.println();
        }
        if(dataMap.get("test2") instanceof  Double){
            System.out.println();
        }
    }
}
