package com.newyu.fx.spi;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
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
public class DatasetImpl implements Dataset<StudentCj> {

    protected Map<String, StudentCj> studentCjMap = new HashMap<>();


    public DatasetImpl add(StudentCj studentCj) {
        studentCjMap.put(studentCj.getZkzh(), studentCj);
        return this;
    }

    public Optional<StudentCj> get(String zkzh) {
        Optional<StudentCj> optionalStudentCj = Optional.ofNullable(studentCjMap.get(zkzh));
        return optionalStudentCj;
    }

    @Override
    public Dataset<StudentCj> filter(Predicate predicate) {
        OpStage stage = new OpStage<StudentCj>(this) {
            @Override
            public Sink opWrapSink(Sink downSink) {
                return new ChainSink<StudentCj>(downSink) {
                    @Override
                    public void accept(StudentCj value) {
                        if (predicate.test(value)) {
                            downSink.accept(value);
                        }
                    }
                };
            }
        };
        return stage;
    }


    @Override
    public Dataset<StudentCj> sort(Comparator<StudentCj> comparator) {
        OpStage stage = new OpStage<StudentCj>(this) {
            @Override
            public Sink opWrapSink(Sink downSink) {
                return new ChainSink<StudentCj>(downSink) {
                    List<StudentCj> list;

                    @Override
                    public void begin(int size) {
                        list = Lists.newArrayList();
                        super.begin(size);
                    }

                    @Override
                    public void end() {
                        list.sort(comparator);
                        downSink.begin(list.size());
                        list.forEach(downSink::accept);
                        downSink.end();
                        list = null;
                    }

                    @Override
                    public void accept(StudentCj value) {
                        list.add(value);
                    }
                };
            }
        };
        return stage;
    }

    @Override
    public List<StudentCj> getList() {
        List<StudentCj> result = Lists.newArrayList();
        Sink<StudentCj> sink = new Sink<StudentCj>() {
            @Override
            public void accept(StudentCj value) {
                result.add(value);
            }
        };
        exec(sink);
        return result;
    }

    private void exec(Sink<StudentCj> sink) {
        Sink<StudentCj> finlaSink = getRootSink(sink);
        List<StudentCj> studentCjs = getRootDatasetStudentCjs();
        finlaSink.begin(studentCjs.size());
        studentCjs.forEach(x -> finlaSink.accept(x));
        finlaSink.end();
    }

    private Dataset<StudentCj> getRootDataset() {
        Dataset<StudentCj> tmp = this;
        while (tmp instanceof OpStage) {
            OpStage tmp2 = (OpStage) tmp;
            tmp = tmp2.getPreviousDataset();
        }
        return tmp;
    }

    private List<StudentCj> getRootDatasetStudentCjs() {
        Dataset<StudentCj> root = getRootDataset();
        DatasetImpl tmp3 = (DatasetImpl) root;
        return Lists.newArrayList(tmp3.studentCjMap.values());
    }

    private Sink<StudentCj> getRootSink(Sink<StudentCj> sink) {
        Dataset<StudentCj> tmp = this;
        while (tmp instanceof OpStage) {
            OpStage tmp2 = (OpStage) tmp;
            sink = tmp2.opWrapSink(sink);
            tmp = tmp2.getPreviousDataset();
        }
        sink = createBeginSink(sink);
        return sink;
    }

    private Sink<StudentCj> createBeginSink(Sink<StudentCj> sink) {
        ChainSink<StudentCj> beginSink = new ChainSink<StudentCj>(sink) {
            @Override
            public void accept(StudentCj value) {
                downSink.accept(value);
            }
        };
        return beginSink;
    }

    private List<StudentCj> getStudentCjs2() {
        return Lists.newArrayList(studentCjMap.values());
    }

    @Override
    public List<GroupDataset<StudentCj>> getGroupDataset(GroupInfo groupInfo) {

        Multimap<GroupValue, StudentCj> groupStudentCjsMap = ArrayListMultimap.create();
        Sink<StudentCj> sink = new Sink<StudentCj>() {
            @Override
            public void accept(StudentCj value) {
                GroupValue groupValue = new GroupValue();
                if (groupInfo != null) {
                    groupValue = getGroupValue(groupInfo, value);
                }
                groupStudentCjsMap.put(groupValue, value);
            }
        };
        exec(sink);
        return createGroupDataset(groupStudentCjsMap);
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

    private List<GroupDataset<StudentCj>> createGroupDataset(Multimap<GroupValue, StudentCj> groupStudentCjsMap) {

        Dataset<StudentCj> root = getRootDataset();
        GroupValue parent = null;
        if (root instanceof GroupDataset) {
            parent = ((GroupDataset) root).getGroupValue();
        }

        List<GroupDataset<StudentCj>> groupDatasets = new ArrayList<>();
        for (GroupValue groupValue : groupStudentCjsMap.keySet()) {
            groupValue.setParent(parent);
            GroupDatasetImpl groupDataset = new GroupDatasetImpl();
            groupDataset.setGroupValue(groupValue);
            groupDataset.setStudentCjs(Lists.newArrayList(groupStudentCjsMap.get(groupValue)));
            groupDatasets.add(groupDataset);
        }
        return groupDatasets;
    }
}
