package com.newyu.es.test.bean;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * ClassName: Segment <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-22 下午5:19 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Data
@Builder
public class Segment {
    private double from;
    private double to;
    private int count;
    private List<Upline> uplines;
}
