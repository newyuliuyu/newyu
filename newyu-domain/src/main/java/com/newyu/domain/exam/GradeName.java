package com.newyu.domain.exam;

import com.newyu.utils.mybatis.NameEnum;

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
public enum GradeName implements NameEnum {
    /**
     *
     */
    OneGrade("一年级", 1),
    /**
     *
     */
    TwoGrade("二年级", 2),
    /**
     *
     */
    ThreeGrade("三年级", 3),
    /**
     *
     */
    FourGrade("四年级", 4),
    /**
     *
     */
    FiveGrade("五年级", 5),
    /**
     *
     */
    SixGrade("六年级", 6),
    /**
     *
     */
    Chuyi("初一", 7),
    /**
     *
     */
    Chuer("初二", 8),
    /**
     *
     */
    Chusan("初三", 9),
    /**
     *
     */
    Gaoyi("高一", 10),
    /**
     *
     */
    Gaoer("高二", 11),
    /**
     *
     */
    Gaosan("高三", 12);

    private String name;
    private int code;

    private GradeName(String name, int code) {
        this.name = name;
        this.code = code;
    }



    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }
}
