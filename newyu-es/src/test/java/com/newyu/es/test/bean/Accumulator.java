package com.newyu.es.test.bean;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: Accumulator <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-23 上午11:21 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Data
@Builder
public class Accumulator {
    @Builder.Default
    private int count;

    public int increase() {
        return count++;
    }
}
