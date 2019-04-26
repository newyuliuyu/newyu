package com.newyu.fx;

/**
 * ClassName: Dataset <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-12 下午3:47 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface GroupDataset<T> extends Dataset<T> {

    /**
     * 获取分组中的值
     *
     * @return
     */
    GroupValue getGroupValue();
}
