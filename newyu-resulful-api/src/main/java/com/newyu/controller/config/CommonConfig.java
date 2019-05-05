package com.newyu.controller.config;


import com.newyu.utils.id.IdGenerator;
import com.newyu.utils.spring.SpringContextUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName: CommonConfig <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-7-31 上午10:21 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Configuration
//@PropertySource("classpath:ezconfig.properties")
public class CommonConfig {

    @Bean
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }

    @Bean
    public IdGenerator idGenerator() {
        return new IdGenerator();
    }

//    @Bean
//    @ConfigurationProperties("ez")
//    public EzConfig ezConfig() {
//        return new EzConfig();
//    }

}
