package com.newyu.fx;

/**
 * ClassName: MeDataset <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-20 上午10:38 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface MeDataset<T> {
    boolean hasNext();

    T[] data();
}
