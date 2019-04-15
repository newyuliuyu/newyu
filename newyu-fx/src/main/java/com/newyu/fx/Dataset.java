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
public interface Dataset {

    /**
     * 根据分析信息对数据集进行分组
     * @param groupInfo
     * @return
     */
    GroupDataset group(GroupInfo groupInfo);
}
