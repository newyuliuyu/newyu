package com.newyu.domain.exam;

import com.newyu.utils.mybatis.CodeEnum;

/**
 * ClassName: LearningSegment <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-23 上午10:49 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public enum LearningSegment implements CodeEnum {
    /**
     *
     */
    Primary_School("小学", 1),
    /**
     *
     */
    Junior_High_School("初中", 2),
    /**
     *
     */
    High_School("高中", 3),
    /**
     *
     */
    Other("其他", 4),
    ;

    private String name;
    private int code;

    private LearningSegment(String name, int code) {
        this.name = name;
        this.code = code;
    }


    @Override
    public int getCode() {
        return code;
    }


    public String getName() {
        return name;
    }
}
