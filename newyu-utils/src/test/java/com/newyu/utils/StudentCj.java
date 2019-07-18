package com.newyu.utils;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: StudentCj <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-15 下午1:42 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Data
@Builder
public class StudentCj {
    private String zkzh;
    private String name;
    private String gender;
    private int year;
    private int wl;
    private String schoolCode;
    private String schoolName;
    private String clazzCode;
    private String clazzName;

    private double zkyuwen;
    private double zkyingyu;
    private double zkshuxue;
    private double zkwuli;
    private double zkhuaxue;
    private double zkshengwu;
    private double zkzhengzhi;
    private double zklishi;
    private double zkdili;
    private double zkzonghe;
    private double zkzf;

    private double gkyuwen;
    private double gkyingyu;
    private double gkshuxue;
    private double gkzonghe;
    private double gkzf;
}
