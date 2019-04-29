package com.newyu.domain.fx;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * ClassName: SubjectDataVersion <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-28 上午10:44 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@Builder
public class SubjectDataVersion {
    private Long subjectId;
    private Long examId;
    private int curVesrion;
    private int previousVesrion;

    public boolean isCalculate() {
        return curVesrion > previousVesrion;
    }
}
