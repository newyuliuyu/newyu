package com.newyu.es.test.bean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * ClassName: SchoolAvg <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-23 下午4:37 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Data
@Builder
public class SchoolAvg {
    private String name;
    private int count;
    @Builder.Default
    private Map<String, Double> avgMap = Maps.newHashMap();
    @Builder.Default
    private List<ClazzAvg> clazzAvgs = Lists.newArrayList();
}
