package com.newyu.controller.commons;

import com.newyu.controller.BaseMVCTest;
import com.newyu.controller.NewyuResulfulApiApplication;
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
 * ClassName: LoggerControllerTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-30 下午1:47 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewyuResulfulApiApplication.class)
@Slf4j
public class LoggerControllerTest extends BaseMVCTest {

    @Test
    public void logInfo() throws Exception {

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/log/info").contentType(MediaType.APPLICATION_JSON);
        ResultActions resultActions = mvc.perform(request);
        String content = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(content);
    }
}