package com.newyu.fx;

import com.newyu.domain.fx.GroupInfo;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

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
public interface Dataset<T> {

    /**
     * 设置过滤条件
     *
     * @param predicate
     * @return
     */
    Dataset<T> filter(Predicate<T> predicate);

    Dataset<T> sort(Comparator<T> comparator);

    /**
     * 获取学生成绩
     *
     * @return
     */
    List<T> getList();

    /**
     * 获取分组数据
     *
     * @return
     */
    List<GroupDataset<T>> getGroupDataset(GroupInfo groupInfo);


}
