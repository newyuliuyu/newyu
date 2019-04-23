package com.newyu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.newyu.domain.exam.Exam;
import com.newyu.domain.exam.ExamState;
import org.junit.Test;

/**
 * ClassName: JsonTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-23 下午1:32 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class JsonTest {

    @Test
    public void examToJson() throws Exception {
        Exam exam=Exam.builder().id(1L).state(ExamState.analyzeWait).build();

        String json  = JSON.toJSONString(exam, SerializerFeature.WriteEnumUsingToString);
        System.out.println(json);
    }
    @Test
    public void jsonToExam() throws Exception {
        String json  = "{\"beginTiem\":0,\"createTime\":0,\"endTime\":0,\"entranceSchoolYear\":0,\"id\":1,\"state\":4}";
//        Exam exam=Json2.fromJson(json,Exam.class);

        Exam exam=JSON.parseObject(json,Exam.class);
        System.out.println();


    }
}
