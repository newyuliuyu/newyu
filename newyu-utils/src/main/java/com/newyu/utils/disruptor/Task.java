package com.newyu.utils.disruptor;

/**
 * ClassName: Task <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-17 下午4:19 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface Task {
    int total();

    boolean next();

    <T> T get();
}
