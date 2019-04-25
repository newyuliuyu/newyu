package com.newyu.domain.exam;

import com.newyu.utils.mybatis.GenericEnum;

/**
 * ClassName: WLType <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-25 下午4:11 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public enum WLType implements GenericEnum {

    /**
     *
     */
    Not_Branch_Subject("不分科", 0),

    /**
     *
     */
    Like("理科", 0),

    /**
     *
     */
    Wenke("文科", 2);

    private String name;
    private int code;

    private WLType(String name, int code) {
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
