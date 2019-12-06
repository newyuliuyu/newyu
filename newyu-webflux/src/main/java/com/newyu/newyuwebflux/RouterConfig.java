package com.newyu.newyuwebflux;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * ClassName: RouterConfig <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-12-2 下午4:52 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
@Configuration
public class RouterConfig {
    @Autowired
    private TimeHandler timeHandler;

    @Bean
    public RouterFunction<ServerResponse> timerRouter() {
        log.debug("=============================timerRouter");
        return RouterFunctions.route(RequestPredicates.GET("/time"), req -> timeHandler.getTime(req))
                .andRoute(RequestPredicates.GET("/date"), timeHandler::getDate)
                .andRoute(RequestPredicates.GET("/times"),timeHandler::sendTimePerSec);  // 这种方式相对于上一行更加简洁
    }
}
