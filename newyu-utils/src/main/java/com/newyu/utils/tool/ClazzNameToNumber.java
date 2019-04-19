package com.newyu.utils.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: ClazzNameToNumber <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 18-8-10 下午1:26 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ClazzNameToNumber {
    private static Pattern numberPattern = Pattern.compile("[0123456789]+");
    private static Pattern chineseNumberpattern = Pattern.compile("[一二三四五六七八九十]+");

    public static int toNum(String clazzName) {

        Matcher matcher = null;
        matcher = numberPattern.matcher(clazzName);
        String num = null;
        if (matcher.find()) {
            num = matcher.group();
        }
        if (num == null) {
            matcher = chineseNumberpattern.matcher(clazzName);
            if (matcher.find()) {
                num = matcher.group();
                System.out.println(num);
                num = NumberHelper.chineseToNumber(num) + "";
            }
        }

        if (num == null) {
            return Integer.MAX_VALUE;
        } else {
            return Integer.parseInt(num);
        }
    }
}
