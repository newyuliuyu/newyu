package com.newyu.es.test.bean;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: Upline <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-22 下午5:18 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Data
@Builder
public class Upline {
    private String name;
    private double from;
    private double to;
    private int count;
}
