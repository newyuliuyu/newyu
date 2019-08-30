package com.newyu.es.test.bean;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * ClassName: Stat <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-22 下午5:24 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Data
@Builder
public class Stat {
    private int count;
    private List<Term> terms;
}
