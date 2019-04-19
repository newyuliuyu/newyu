/** 
 * Project Name:easydata 
 * File Name:SpringMVCExceptionResolver.java 
 * Package Name:com.ez.commons.web 
 * Date:2017年3月2日下午3:31:57 
 * Copyright (c) 2017, easytnt All Rights Reserved. 
 * 
 */
package com.newyu.utils.spring;

import com.newyu.utils.exception.ExceptionToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName: SpringMVCExceptionResolver <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2017年3月2日 下午3:31:57 <br/>
 * 
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class SpringMVCExceptionResolver extends SimpleMappingExceptionResolver {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.error("系统异常 ：{}", ExceptionToString.toString(ex));
		String viewName = null;
		processResponseStatus(request, response, ex);
		if (!isAjax(request)) {
		}
		return createModelAndView(viewName, ex);
	}

	private boolean isAjax(HttpServletRequest request) {
		return (request.getHeader("accept").indexOf("application/json") > -1
				|| (request.getHeader("X-Requested-With") != null
						&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)
				|| (request.getHeader("Content-Type") != null
						&& request.getHeader("Content-Type").indexOf("multipart/form-data") > -1));
	}

	private String processResponseStatus(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		String viewName = determineViewName(ex, request);
		Integer statusCode = determineStatusCode(request, viewName);
		if (statusCode != null) {
			applyStatusCodeIfPossible(request, response, statusCode);
		}
		return viewName;
	}

	private ModelAndView createModelAndView(String viewName, Exception ex) {
		String code = "0";
		String message = "很抱歉，系统在处理您的请求产生未知错误，请联系管理员";
		String detail = ExceptionToString.toString(ex);
		message = ex.getMessage();
		Responser rs = new Responser.Builder().failure().code(code).msg(message).detail(detail).create();
		if (viewName != null && !"".equals(viewName)) {
			return ModelAndViewFactory.newModelAndViewFor(viewName, rs).build();
		} else {
			return ModelAndViewFactory.newModelAndViewFor(rs).build();
		}
	}
}
