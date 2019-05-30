/**
 * Project Name:easydata.commons
 * File Name:NumberHelper.java
 * Package Name:com.ez.data.commons.util
 * Date:2017年4月19日下午1:35:40
 * Copyright (c) 2017, easytnt All Rights Reserved.
 */
package com.newyu.utils.tool;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * ClassName: NumberHelper <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2017年4月19日 下午1:35:40 <br/>
 *
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class NumberHelper {

    public static boolean isNumeric(final CharSequence cs) {
        if (StringUtils.isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if ((!Character.isDigit(cs.charAt(i))
                    && cs.charAt(i) != '.') || (cs.charAt(i) == '.' && (i == 0 || i == sz - 1))) {
                return false;
            }
        }
        return true;
    }

    public static String toChinese(int num) {
        if (num == 0) {
            return "零";
        }
        // 节权位标识
        int unitPos = 0;
        String all = new String();
        // 中文数字字符串
        String chineseNum = new String();
        // 下一小结是否需要补零
        boolean needZero = false;
        String strIns = new String();
        while (num > 0) {
            // 取最后面的那一个小节
            int section = num % 10000;
            if (needZero) {
                // 判断上一小节千位是否为零，为零就要加上零
                all = Tool.chnNumChar[0] + all;
            }
            // 处理当前小节的数字,然后用chineseNum记录当前小节数字
            chineseNum = sectionTOChinese(section, chineseNum);
            if (section != 0) {
                // 此处用if else 选择语句来执行加节权位
                // 当小节不为0，就加上节权位
                strIns = Tool.chnUnitSection[unitPos];
                chineseNum = chineseNum + strIns;
            } else {
                // 否则不用加
                strIns = Tool.chnUnitSection[0];
                chineseNum = strIns + chineseNum;
            }
            all = chineseNum + all;
            chineseNum = "";
            needZero = (section < 1000) && (section > 0);
            num = num / 10000;
            unitPos++;
        }
        return all;
    }

    private static String sectionTOChinese(int section, String chineseNum) {
        // 小节部分用独立函数操作
        String setionChinese = new String();
        // 小节内部的权值计数器
        int unitPos = 0;
        // 小节内部的制零判断，每个小节内只能出现一个零
        boolean zero = true;
        while (section > 0) {
            // 取当前最末位的值
            int v = section % 10;
            if (v == 0) {
                if (!zero) {
                    // 需要补零的操作，确保对连续多个零只是输出一个
                    zero = true;
                    chineseNum = Tool.chnNumChar[0] + chineseNum;
                }
            } else {
                // 有非零的数字，就把制零开关打开
                zero = false;
                // 对应中文数字位
                setionChinese = Tool.chnNumChar[v];
                // 对应中文权位
                setionChinese = setionChinese + Tool.chnUnitChar[unitPos];
                chineseNum = setionChinese + chineseNum;
            }
            unitPos++;
            section = section / 10;
        }

        return chineseNum;
    }

    public static int chineseToNumber(String str) {
        String str1 = new String();
        String str2 = new String();
        String str3 = new String();
        int k = 0;
        boolean dealflag = true;
        for (int i = 0; i < str.length(); i++) {
            // 先把字符串中的“零”除去
            if ('零' == (str.charAt(i))) {
                str = str.substring(0, i) + str.substring(i + 1);
            }
        }
        String chineseNum = str;
        for (int i = 0; i < chineseNum.length(); i++) {
            if (chineseNum.charAt(i) == '亿') {
                // 截取亿前面的数字，逐个对照表格，然后转换
                str1 = chineseNum.substring(0, i);
                k = i + 1;
                // 已经处理
                dealflag = false;
            }
            if (chineseNum.charAt(i) == '万') {
                str2 = chineseNum.substring(k, i);
                str3 = str.substring(i + 1);
                // 已经处理
                dealflag = false;

            }
        }
        if (dealflag) {
            // 如果没有处理
            str3 = chineseNum;
        }
        int result = sectionChinese(str1) * 100000000 + sectionChinese(str2) * 10000 + sectionChinese(str3);
        return result;
    }

    private static int sectionChinese(String str) {
        int value = 0;
        int sectionNum = 1;
        for (int i = 0; i < str.length(); i++) {
            int v = Tool.intList.get(str.charAt(i));
            if (v == 10 || v == 100 || v == 1000) {
                // 如果数值是权位则相乘
                sectionNum = v * sectionNum;
                value = value + sectionNum;
            } else if (i == str.length() - 1) {
                value = value + v;
            } else {
                sectionNum = v;
            }
        }
        return value;
    }
}

class Tool {
    /**
     * 数字位
     */
    public static String[] chnNumChar = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    public static char[] chnNumChinese = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};
    /**
     * 节权位
     */
    public static String[] chnUnitSection = {"", "万", "亿", "万亿"};
    /**
     * 权位
     */
    public static String[] chnUnitChar = {"", "十", "百", "千"};
    public static HashMap<Character, Integer> intList = new HashMap();

    static {
        for (int i = 0; i < chnNumChar.length; i++) {
            intList.put(chnNumChinese[i], i);
        }

        intList.put('十', 10);
        intList.put('百', 100);
        intList.put('千', 1000);
    }

}
