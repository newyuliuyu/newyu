/** 
 * Project Name:easytnt-commons 
 * File Name:Json.java 
 * Package Name:com.ez.commons.util 
 * Date:2016年5月5日下午7:38:10 
 * Copyright (c) 2016, easytnt All Rights Reserved. 
 * 
 */
package com.newyu.utils.json;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * ClassName: Json <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2016年5月5日 下午7:38:10 <br/>
 * 
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class Json2 {
	public static String toJson(Object src) {
		String json = JSON.toJSONString(src);
		return json;
	}

	/**
	 * 判断字符串是否是json格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isJson(String str) {
		boolean result = false;
		try {
			Json2.fromJson(str, Map.class);
			result = true;
		} catch (Exception e) {
		}

		return result;
	}

	public static <T> T fromJson(String json, Class<T> type) {
		T obj = JSON.parseObject(json, type);
		return obj;
	}

}
