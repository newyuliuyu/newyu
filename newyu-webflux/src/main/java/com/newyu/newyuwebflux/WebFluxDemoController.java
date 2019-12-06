package com.newyu.newyuwebflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * ClassName: WebFluxDemoController <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-12-2 下午4:40 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@RestController
public class WebFluxDemoController {

    @GetMapping("/hello")
    public Mono<String> hello(){
        return Mono.just("Welcome to reactive world ~ spring web flux");
    }
}
