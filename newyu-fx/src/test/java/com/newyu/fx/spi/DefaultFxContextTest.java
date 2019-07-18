package com.newyu.fx.spi;

import com.newyu.fx.AppConfig;
import com.newyu.fx.FxContext;
import com.newyu.service.ExamService;
import com.newyu.service.ExamXSubjectXItemService;
import com.newyu.service.FxParamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClassName: DefaultFxContextTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-6-14 上午11:02 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class DefaultFxContextTest {

    @Autowired
    private ExamService examService;
    @Autowired
    private ExamXSubjectXItemService examXSubjectXItemService;
    @Autowired
    private FxParamService fxParamService;

    @Test
    public void initContext() throws Exception {
        FxContext context = DefaultFxContext.builder()
                .examId(1139341162646257664L)
                .examService(examService)
                .examXSubjectXItemService(examXSubjectXItemService)
                .fxParamService(fxParamService)
                .build();
        System.out.println();
    }

}