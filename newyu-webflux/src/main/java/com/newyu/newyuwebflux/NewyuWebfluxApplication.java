package com.newyu.newyuwebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class NewyuWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewyuWebfluxApplication.class, args);
    }

}
