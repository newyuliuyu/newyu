package com.newyu.fx;

import com.newyu.domain.exam.StudentCj;
import com.newyu.domain.fx.GroupInfo;
import com.newyu.fx.spi.GroupDatasetImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public interface Dataset {

    /**
     * 设置过滤条件
     *
     * @param predicate
     * @return
     */
    Dataset filter(Predicate predicate);

    /**
     * 根据分析信息对数据集进行分组
     *
     * @param groupInfo
     * @return
     */
    Dataset group(GroupInfo groupInfo);

    /**
     * 获取学生成绩
     *
     * @return
     */
    List<StudentCj> getStudentCjs();

    /**
     * 获取分组数据
     *
     * @return
     */
    List<GroupDataset> getGroupDataset();

    /**
     * 通过分组的学生成绩创建一个分组数据结合
     *
     * @param groupStudentCjsMap
     * @return
     */
    default List<GroupDataset> createGroupDataset(Map<GroupValue,
            List<StudentCj>> groupStudentCjsMap) {
        List<GroupDataset> groupDatasets = new ArrayList<>();
        for (GroupValue groupValue : groupStudentCjsMap.keySet()) {
            GroupDatasetImpl groupDataset = new GroupDatasetImpl();
            groupDataset.setGroupValue(groupValue);
            groupDataset.setStudentCjs(groupStudentCjsMap.get(groupValue));
            groupDatasets.add(groupDataset);
        }
        return groupDatasets;
    }

}
