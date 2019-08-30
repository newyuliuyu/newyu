package com.newyu.es.test.bean;

import com.google.common.collect.Maps;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * ClassName: EffectiveScore <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-23 下午4:02 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Data
@Builder
public class EffectiveScore {
    private String name;
    private int count;
    private double uplineScore;
    private double coefficient;
    @Builder.Default
    private Map<String, Double> subjectAvgMap = Maps.newHashMap();

    public double getScore(String subject) {
        if (subject.contains("zf")) {
            return uplineScore;
        }
        double avg = subjectAvgMap.get(subject);
        return avg*coefficient;
    }

    public void calcluateCoefficient(String... subjects) {
        double avgSum = 0d;
        for (String subject : subjects) {
            double avg = subjectAvgMap.get(subject);
            avgSum += avg;
        }
        coefficient = uplineScore / avgSum;
    }
}
