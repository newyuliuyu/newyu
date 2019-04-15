package com.newyu.fx;

/**
 * ClassName: ReaderDataset <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-15 下午5:32 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface ReaderDataset {
    /**
     * 读取数据集
     * @param context
     * @return
     */
    Dataset read(FxContext context);
}
