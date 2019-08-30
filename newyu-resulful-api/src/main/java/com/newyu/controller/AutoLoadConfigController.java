package com.newyu.controller;

import com.newyu.utils.spring.ModelAndViewFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: ExamController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-30 下午1:56 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
@RestController
@RequestMapping("/autoconfig")
@RefreshScope
public class AutoLoadConfigController {

    @Value("${myName}")
    private String myName;

    @RequestMapping()
    public ModelAndView autoConfig( HttpServletRequest request,
                                   HttpServletResponse responese) throws Exception {
        log.debug("auto config ...");


        System.out.println(myName);

        return ModelAndViewFactory.instance().with("myName", myName).build();
    }
}
