package com.newyu.fx.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.newyu.domain.org.Clazz;
import com.newyu.domain.org.TeachClazz;
import com.newyu.utils.tool.NumberHelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: SubjectHelper <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 17-11-21 下午5:59 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ClazzHelper {
    private static Pattern numPattern = Pattern.compile("[0123456789]+");
    private static Pattern chineseNumPattern = Pattern.compile("[一二三四五六七八九十]+");

    public static List<Clazz> getClazzes(List<Clazz> clazzes, int wl) {
        if (wl == 0) {
            return clazzes;
        }
        List<Clazz> result = Lists.newArrayList();
        for (Clazz clazz : clazzes) {
            if (clazz.getWl() == wl) {
                result.add(clazz);
            }
        }
        return result;
    }

    public static List<Clazz> sort(List<Clazz> clazzes) {
        Ordering<Clazz> ordering = Ordering.from(new Comparator<Clazz>() {
            @Override
            public int compare(Clazz o1, Clazz o2) {
                int num1 = getNum(o1.getName());
                int num2 = getNum(o2.getName());
                return Ints.compare(num1, num2);
            }
        });
        Collections.sort(clazzes, ordering);
        return clazzes;
    }

    public static List<TeachClazz> sort2(List<TeachClazz> teachClazzes) {
        Ordering<TeachClazz> ordering = Ordering.from(new Comparator<TeachClazz>() {
            @Override
            public int compare(TeachClazz o1, TeachClazz o2) {
                int num1 = getNum(o1.getName());
                int num2 = getNum(o2.getName());
                return Ints.compare(num1, num2);
            }
        });
        Collections.sort(teachClazzes, ordering);
        return teachClazzes;
    }

    public static int getNum(String clazzName) {

        Matcher matcher = numPattern.matcher(clazzName);
        String num = null;
        if (matcher.find()) {
            num = matcher.group();
        }
        if (num == null) {
            matcher = chineseNumPattern.matcher(clazzName);
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
