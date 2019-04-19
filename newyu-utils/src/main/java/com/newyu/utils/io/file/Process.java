/** 
 * Project Name:easydata.etl 
 * File Name:Process.java 
 * Package Name:com.ez.etl 
 * Date:2017年3月14日上午11:07:24 
 * Copyright (c) 2017, easytnt All Rights Reserved. 
 * 
 */
package com.newyu.utils.io.file;

/**
 * ClassName: Process <br/>
 * Function: <br/>
 * Reason:  <br/>
 * date: 2017年3月14日 上午11:07:24 <br/>
 * 
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public interface Process {
	public Rowdata process(Rowdata rowdata);
}
