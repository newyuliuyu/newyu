package com.newyu.domain.exam;

import com.newyu.utils.mybatis.CodeEnum;

/**
 * ClassName: ItemType <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-24 上午11:25 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public enum  ItemType implements CodeEnum {
    /**
     *
     */
    Single_Select("单选题",1),
    /**
     *
     */
    Multi_Select("多选题",2),
    /**
     *
     */
    Not_Select("主观题",0);

    private String name;

    private int code;

    private ItemType(String name, int code) {
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
