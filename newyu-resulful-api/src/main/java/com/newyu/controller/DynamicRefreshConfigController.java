package com.newyu.controller;

import com.newyu.utils.spring.ModelAndViewFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: DynamicRefreshConfigController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-11-27 下午2:46 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
@RestController
@RefreshScope
public class DynamicRefreshConfigController {

    @Value("${my.uuid}")
    private String myUuid;

    @Autowired
    private ContextRefresher contextRefresher;

    @GetMapping(path = "/show")
    public ModelAndView show(HttpServletRequest request,
                             HttpServletResponse responese) throws Exception {
        log.debug("show function ...");

        return ModelAndViewFactory.instance().with("myuuid", myUuid).build();
    }

    @GetMapping(path = "/refresh")
    public ModelAndView refresh(HttpServletRequest request,
                                HttpServletResponse responese) throws Exception {
        log.debug("refrechs function ...");
        new Thread(()->contextRefresher.refresh()).start();
        return show(request,responese);
    }


}
