/** 
 * Project Name:easydata.etl 
 * File Name:FileProcess.java 
 * Package Name:com.ez.etl.file 
 * Date:2017年3月14日上午11:06:28 
 * Copyright (c) 2017, easytnt All Rights Reserved. 
 * 
 */
package com.newyu.utils.io.file;

/**
 * ClassName: FileProcess <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2017年3月14日 上午11:06:28 <br/>
 * 
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public interface FileProcess extends Process {

	public HeaderMetadata getHeaderMetadata();

	public boolean next();

	public Rowdata getRowdata();

	public boolean close();
}
