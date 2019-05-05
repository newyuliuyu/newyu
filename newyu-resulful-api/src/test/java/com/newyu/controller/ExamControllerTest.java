package com.newyu.controller;

import com.newyu.domain.exam.Exam;
import com.newyu.utils.json.Json2;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * ClassName: ExamControllerTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-30 下午1:59 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewyuResulfulApiApplication.class)
@Slf4j
public class ExamControllerTest extends BaseMVCTest {

    @Test
    public void updateExam() throws Exception {
        Exam exam = Exam.builder().id(1L).name("test").build();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/exam/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json2.toJson(exam));
        ResultActions resultActions = mvc.perform(request);
        String content = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(content);
    }
}