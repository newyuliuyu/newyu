package com.newyu.fx.spi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newyu.domain.exam.StudentCj;
import com.newyu.domain.fx.GroupInfo;
import com.newyu.fx.Dataset;
import com.newyu.fx.GroupDataset;
import com.newyu.fx.GroupValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * ClassName: DatasetImpl <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-24 下午4:03 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class DatasetImpl implements Dataset {

    protected Map<String, StudentCj> studentCjMap = new HashMap<>();
    private List<StudentCj> filterStudentCjs = null;
    private GroupInfo groupInfo = null;
    private Predicate<StudentCj> predicate = null;

    public DatasetImpl add(StudentCj studentCj) {
        studentCjMap.put(studentCj.getZkzh(), studentCj);
        return this;
    }

    public Optional<StudentCj> get(String zkzh) {
        Optional<StudentCj> optionalStudentCj = Optional.ofNullable(studentCjMap.get(zkzh));
        return optionalStudentCj;
    }

    @Override
    public Dataset filter(Predicate predicate) {
        filterStudentCjs = null;
        this.predicate = predicate;
        return this;
    }

    @Override
    public Dataset group(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
        return this;
    }

    private Map<GroupValue, List<StudentCj>> groupStudentCjs() {
        if (groupInfo == null) {
            Map<GroupValue, List<StudentCj>> groupStudentCjsMap = Maps.newHashMap();
            GroupValue groupValue = new GroupValue();
            groupStudentCjsMap.put(groupValue, getStudentCjs2());
            return groupStudentCjsMap;
        }
        Map<GroupValue, List<StudentCj>> groupStudentCjsMap = getStudentCjs2().stream().collect(Collectors.groupingBy(studentCj -> {
            GroupValue groupValue = getGroupValue(groupInfo, studentCj);
            return groupValue;
        }));
        return groupStudentCjsMap;
    }

    private GroupValue getGroupValue(GroupInfo groupInfo, StudentCj studentCj) {
        GroupValue groupValue = new GroupValue();
        groupValue.setGroupInfo(groupInfo);
        List<String> fileds = groupInfo.getGroupFileds();
        try {
            for (String filed : fileds) {
                Object value = PropertyUtils.getProperty(studentCj, filed);
                if (value == null) {
                    String msg = MessageFormat.format("从学生[{0}]获取[{1}]属性的值为空", studentCj, filed);
                    throw new RuntimeException(msg);
                }
                groupValue.addValue(filed, value);
            }
        } catch (Exception e) {
            String msg = MessageFormat.format("分组过程中，从学生[{0}]获取[{1}]属性出错", studentCj, groupInfo);
            throw new RuntimeException(msg, e);
        }
        return groupValue;
    }

    @Override
    public List<StudentCj> getStudentCjs() {
        return Collections.unmodifiableList(getStudentCjs2());
    }

    private List<StudentCj> getStudentCjs2() {

        if (predicate == null || filterStudentCjs == null) {
            filterStudentCjs = Lists.newArrayList(studentCjMap.values());
        } else if (predicate != null && filterStudentCjs == null) {
            filterStudentCjs = studentCjMap.values().stream().filter(predicate).collect(Collectors.toList());
        }
        return filterStudentCjs;
    }

    @Override
    public List<GroupDataset> getGroupDataset() {

        Map<GroupValue, List<StudentCj>> groupStudentCjsMap = groupStudentCjs();
        return createGroupDataset(groupStudentCjsMap);
    }
}
