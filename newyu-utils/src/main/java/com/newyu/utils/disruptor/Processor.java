package com.newyu.utils.disruptor;

/**
 * ClassName: Processor <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-16 下午4:40 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface Processor<T> {
    Class getDataClass();

    void process(T data);
}
