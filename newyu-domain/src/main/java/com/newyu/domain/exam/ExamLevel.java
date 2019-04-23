package com.newyu.domain.exam;

import com.newyu.utils.mybatis.GenericEnum;

/**
 * ClassName: ExamType <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-23 上午10:51 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public enum ExamLevel implements GenericEnum {
    /**
     *
     */
    Provice_Exam("省级", 1),
    /**
     *
     */
    City_Exam("地市级考试", 2),
    /**
     *
     */
    County_Exam("区县级考试", 3),
    /**
     *
     */
    School_Exam("学校级考试", 4),
    /**
     *
     */
    Union_Exam("联考", 5);

    private String name;
    private int code;

    private ExamLevel(String name, int code) {
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
