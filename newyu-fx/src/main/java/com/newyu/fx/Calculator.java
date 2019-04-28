package com.newyu.fx;

import com.newyu.domain.exam.StudentCj;
import com.newyu.domain.exam.Subject;

import java.util.List;

/**
 * ClassName: Calculator <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-28 上午9:52 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface Calculator {
    void calculate(Subject subject, List<StudentCj> dataset, FxContext fxContext);
}
