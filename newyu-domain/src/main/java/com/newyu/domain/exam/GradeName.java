package com.newyu.domain.exam;

import com.newyu.utils.mybatis.GenericEnum;

/**
 * ClassName: GradeName <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-23 上午11:10 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public enum GradeName implements GenericEnum {
    /**
     *
     */
    Grade1("一年级", 1),
    /**
     *
     */
    Grade2("二年级", 2),
    /**
     *
     */
    Grade3("三年级", 3),
    /**
     *
     */
    Grade4("四年级", 4),
    /**
     *
     */
    Grade5("五年级", 5),
    /**
     *
     */
    Grade6("六年级", 6),
    /**
     *
     */
    Grade7("初一", 7),
    /**
     *
     */
    Grade8("初二", 8),
    /**
     *
     */
    Grade9("初三", 9),
    /**
     *
     */
    Grade10("高一", 10),
    /**
     *
     */
    Grade11("高二", 11),
    /**
     *
     */
    Grade12("高三", 12);

    private String name;
    private int code;

    private GradeName(String name, int code) {
        this.name = name;
        this.code = code;
    }


    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
