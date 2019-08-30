/**
 * Project Name:ezr.zhaoqing
 * File Name:NumberFormatTools.java
 * Package Name:com.ez.framwork.fx.utils
 * Date:2016年12月23日下午3:33:50
 * Copyright (c) 2016, easytnt All Rights Reserved.
 */
package com.newyu.utils.tool;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * ClassName: NumberFormatTools <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年12月23日 下午3:33:50 <br/>
 *
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class NumberFormatTools {
    private DecimalFormat format;
    private DecimalFormat percentFormat;

    public static NumberFormatTools newInstance() {
        return new NumberFormatTools();
    }

    public NumberFormatTools() {
        this("0.##", "0.##%");
    }

    public NumberFormatTools(String format) {
        this(format, format + "%");
    }

    public NumberFormatTools(String numberFormat, String percentFormat) {
        this.format = new DecimalFormat(numberFormat);
        this.format.setRoundingMode(RoundingMode.HALF_UP);
        this.percentFormat = new DecimalFormat(percentFormat);
        this.percentFormat.setRoundingMode(RoundingMode.HALF_UP);
    }

    public static String format(Double vlaue, String format) {
        DecimalFormat tmpformat = new DecimalFormat(format);
        tmpformat.setRoundingMode(RoundingMode.HALF_UP);
        vlaue = Double.isNaN(vlaue) ? 0d : vlaue;
        return tmpformat.format(vlaue);
    }

    public String format(Double score) {
        score = Double.isNaN(score) || Double.isInfinite(score) ? 0d : score;
        return format.format(score);
    }

    public Double formatReturnDouble(Double score) {
        score = Double.isNaN(score) || Double.isInfinite(score) ? 0d : score;
        return Double.parseDouble(format.format(score));
    }

    public String percentFormat(Double score) {
        score = Double.isNaN(score) || Double.isInfinite(score) ? 0d : score;
        return percentFormat.format(score);
    }

    public String percentFormatNoSymbol(Double score) {
        score = Double.isNaN(score) || Double.isInfinite(score) ? 0d : score;
        return percentFormat.format(score).replaceAll("%", "");
    }

    public Double percentFormatNoSymbolDouble(Double score) {
        score = Double.isNaN(score) || Double.isInfinite(score) ? 0d : score;
        String value = percentFormat.format(score).replaceAll("%", "");
        return Double.parseDouble(value);
    }

    public String clearZero(Double score) {
        score = Double.isNaN(score) || Double.isInfinite(score) ? 0d : score;
        return format(score, "#####.#######");

//		score = Double.isNaN(score) ? 0d : score;
//		return clearZero(score.toString());
    }

    public String clearZero(String score) {
        double value = Double.parseDouble(score);
        return clearZero(value);
//		char[] chars = score.toCharArray();
//		int position = chars.length - 1;
//		for (int i = chars.length - 1; i >= 0; i--) {
//			if (chars[i] == '.') {
//				position = i;
//				break;
//			} else if (chars[i] != '0') {
//				position = i + 1;
//				break;
//			}
//		}
//		return score.toString().substring(0, position);
    }

}
