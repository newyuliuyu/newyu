/** 
 * Project Name:easytnt-ez 
 * File Name:ScoreInfo.java 
 * Package Name:com.ez.framwork.fx.stat 
 * Date:2016年8月10日下午4:08:20 
 * Copyright (c) 2016, easytnt All Rights Reserved. 
 * 
 */
package com.newyu.domain.fx;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * ClassName: ScoreInfo <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 2016年8月10日 下午4:08:20 <br/>
 * 
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@Builder
public class ScoreInfo {
	private Double score;
	private int num;
	private int rank;
	private int backRank;


}
