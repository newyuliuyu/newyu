package com.newyu.es.test.bean;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: Subject <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-24 上午11:28 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Data
@Builder
public class Subject {
    private String name;
    private String code;
}
