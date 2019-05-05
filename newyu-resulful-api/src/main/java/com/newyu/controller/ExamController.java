package com.newyu.controller;

import com.newyu.domain.exam.Exam;
import com.newyu.utils.spring.ModelAndViewFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/exam")
public class ExamController {

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateExam(@RequestBody Exam exam,
                                   HttpServletRequest request,
                                   HttpServletResponse responese) throws Exception {
        log.debug("exam update");


        System.out.println();

        return ModelAndViewFactory.instance().with("test", true).build();
    }
}
