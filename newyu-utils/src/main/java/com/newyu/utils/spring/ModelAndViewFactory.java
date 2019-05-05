/**
 * Project Name:easytnt-commons
 * File Name:ModelAndViewFactory.java
 * Package Name:com.easytnt.commons.web
 * Date:2016年3月25日下午2:59:01
 * Copyright (c) 2016, easytnt All Rights Reserved.
 */
package com.newyu.utils.spring;

import com.google.common.collect.Maps;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * ClassName: ModelAndViewFactory <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2016年3月25日 下午2:59:01 <br/>
 *
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class ModelAndViewFactory {
    private String view;
    private Map<String, Object> model = Maps.newHashMap();

    private ModelAndViewFactory(String view) {
        this.view = view;
    }

    public ModelAndViewFactory with(String name, Object value) {
        model.put(name, value);
        return this;
    }

    public ModelAndViewFactory setView(String view) {
        this.view = view;
        return this;
    }

    public ModelAndView build() {
        return new ModelAndView(view, model);
    }

    public static ModelAndViewFactory instance() {
        return new ModelAndViewFactory("");
    }

    public static ModelAndViewFactory instance(String view) {
        return new ModelAndViewFactory(view);
    }
}
