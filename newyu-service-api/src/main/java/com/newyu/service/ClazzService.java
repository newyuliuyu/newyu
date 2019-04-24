package com.newyu.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.newyu.domain.org.Clazz;
import com.newyu.domain.org.TeachClazz;
import com.newyu.utils.tool.ClazzNameToNumber;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ClassName: ClazzService <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-24 下午1:22 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface ClazzService {


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
                int num1 = ClazzNameToNumber.toNum(o1.getName());
                int num2 = ClazzNameToNumber.toNum(o2.getName());
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
                int num1 = ClazzNameToNumber.toNum(o1.getName());
                int num2 = ClazzNameToNumber.toNum(o2.getName());
                return Ints.compare(num1, num2);
            }
        });
        Collections.sort(teachClazzes, ordering);
        return teachClazzes;
    }
}
