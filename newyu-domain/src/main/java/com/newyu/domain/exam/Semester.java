package com.newyu.domain.exam;

import com.newyu.utils.mybatis.GenericEnum;

/**
 * ClassName: EntranceSchoolYear <br/>
 * Function:  学期 <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-23 上午10:45 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public enum Semester implements GenericEnum {
    /**
     *
     */
    LAST_Semester("上学期",1),
    /**
     *
     */
    NEXT_Semester("下学期",2);

    private String name;
    private int code;

    private Semester(String name, int code) {
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
