/**
 * Project Name:easytnt-commons
 * File Name:ModelAndViewFactory.java
 * Package Name:com.easytnt.commons.web
 * Date:2016年3月25日下午2:59:01
 * Copyright (c) 2016, easytnt All Rights Reserved.
 */
package com.newyu.utils.spring;

import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

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
    public static ModelAndViewFactory newModelAndViewFor() {
        return ModelAndViewFactory.newModelAndViewFor("/nothing", Boolean.TRUE);
    }

    public static ModelAndViewFactory newModelAndViewFor(Responser responser) {
        return new ModelAndViewFactory("/nothing").with(Responser.ModelName, responser);
    }

    public static ModelAndViewFactory newModelAndViewFor(String viewName, Responser responser) {
        return new ModelAndViewFactory(viewName).with(Responser.ModelName, responser);
    }

    public static ModelAndViewFactory newModelAndViewFor(String viewName) {
        return ModelAndViewFactory.newModelAndViewFor(viewName, Boolean.TRUE);
    }

    public static ModelAndViewFactory newModelAndViewFor(String viewName, Boolean isSuccessed) {
        if (isSuccessed.equals(Boolean.TRUE)) {
            return new ModelAndViewFactory(viewName).with(Responser.ModelName,
                    new Responser.Builder().success().create());
        } else {
            return new ModelAndViewFactory(viewName).with(Responser.ModelName,
                    new Responser.Builder().failure().create());
        }
    }

    private HashMap<String, Object> model;

    private String viewName;

    private ModelAndViewFactory(String viewName) {
        this.viewName = viewName;
        this.model = new HashMap<>();
    }

    public ModelAndViewFactory with(String modelName, Object model) {
        this.model.put(modelName, model);
        return this;
    }

    public ModelAndView build() {
        return new ModelAndView(this.viewName, this.model);
    }
}
