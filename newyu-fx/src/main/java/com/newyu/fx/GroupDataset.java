package com.newyu.fx;

import java.util.List;

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
public interface GroupDataset extends Dataset {

    /**
     * 获取分组中的只
     *
     * @return
     */
    List<GroupValue> getGroupValue();
}
