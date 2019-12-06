package com.newyu.controller.config;


import com.newyu.controller.servlet.MyServlet2;
import com.newyu.utils.id.IdGenerator;
import com.newyu.utils.spring.SpringContextUtil;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
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

    @Value("${my.test}")
    private String myTest;

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

//    @Bean
//    public ConfigurableServletWebServerFactory webServerFactory() {
//        TomcatServletWebServerFactory tomcatFactory = new TomcatServletWebServerFactory();
//        tomcatFactory.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
////        tomcatFactory.setPort(9999);
////        tomcatFactory.setContextPath("");
//        return tomcatFactory;
//    }

    @Bean
    public ServletRegistrationBean myServlet2() {
        MyServlet2 myServlet2 = new MyServlet2();
        ServletRegistrationBean registration = new ServletRegistrationBean(myServlet2);
        registration.setEnabled(true);
        registration.addUrlMappings("/my-servlet2");
        return registration;
    }

    class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {
        @Override
        public void customize(Connector connector) {
            Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
            protocol.setAcceptCount(100);
            //设置最大连接数
            protocol.setMaxConnections(500);
            protocol.setConnectionTimeout(60000);
            //设置最大线程数
            protocol.setMaxThreads(50);
            protocol.setMinSpareThreads(20);
        }
    }

}
