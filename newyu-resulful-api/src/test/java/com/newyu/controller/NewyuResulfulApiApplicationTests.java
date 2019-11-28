package com.newyu.controller;

import com.newyu.utils.spring.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewyuResulfulApiApplicationTests {

    @Test
    public void contextLoads() {
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();

        if(applicationContext instanceof AbstractApplicationContext){
            System.out.println(true);
        }

        BeanFactory beanFactory = applicationContext.getParentBeanFactory();
        System.out.println();
//        applicationContext
    }

}
