package com.newyu.controller.commons;

import com.newyu.controller.BaseMVCTest;
import com.newyu.controller.NewyuResulfulApiApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;

/**
 * ClassName: UploadFileControllerTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-30 上午10:58 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewyuResulfulApiApplication.class)
@Slf4j
public class UploadFileControllerTest  extends BaseMVCTest {

    @Test
    public void upload() throws Exception {
        String file = "/home/liuyu/tmp/excle/a.xls";

        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("files", "a.xls", "application/vnd_ms-excel", new FileInputStream(new File(file)));
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("files", "a.xls", "application/vnd_ms-excel", new FileInputStream(new File(file)));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.multipart("/upload")
                .file(mockMultipartFile1).file(mockMultipartFile2);

        ResultActions resultActions = mvc.perform(request);
        String content = resultActions.andReturn().getResponse().getContentAsString();
        log.debug(content);
    }
}