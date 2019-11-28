package com.newyu.controller.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * ClassName: DynamicEnvironmentPostProcessor <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-11-27 上午11:30 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Component
public class DynamicEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

       System.out.println("*********************DynamicEnvironmentPostProcessor******************************************");
        Properties properties = new Properties();
        properties.setProperty("my.test","false");
        PropertiesPropertySource propertySource = new PropertiesPropertySource("dynamic", properties);


        environment.getPropertySources().addLast(propertySource);
    }
}
