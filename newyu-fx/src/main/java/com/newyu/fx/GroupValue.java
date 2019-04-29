package com.newyu.fx;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.newyu.domain.fx.FxFiled;
import com.newyu.domain.fx.GroupInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.PropertyUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: GroupValue <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-15 下午5:31 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Setter
@Getter
@EqualsAndHashCode(of = {"valueMap", "parent"})
public class GroupValue {
    private GroupValue parent;
    private GroupInfo groupInfo;
    private Map<String, Object> valueMap = Maps.newHashMap();

    public GroupValue addValue(String name, Object value) {
        valueMap.put(toLowerCaseName(name), value);
        return this;
    }

    public <T> T getValue(String name) {
        Object value = valueMap.get(toLowerCaseName(name));
        return (T) value;
    }

    private String toLowerCaseName(String name) {
        return name.toLowerCase();
    }

    public boolean isHasData() {
        return !valueMap.isEmpty();
    }

    public List<FxData> fetchFxData() {
        List<FxData> result = Lists.newArrayList();
        if (parent != null) {
            result.addAll(parent.fetchFxData());
        }
        if (valueMap.isEmpty()) {
            List<FxData> fxData = valueMap.entrySet().stream()
                    .flatMap(x -> createFxData(x).stream())
                    .collect(Collectors.toList());
            result.addAll(fxData);
        }
        return result;
    }

    private List<FxData> createFxData(Map.Entry<String, Object> entry) {
        List<String> names = filedNames(entry.getKey(), entry.getValue());
        List<Object> values = filedValues(entry);
        List<FxData> fxDatas = Lists.newArrayList();
        for (int i = 0; i < names.size(); i++) {
            fxDatas.add(FxData.builder().name(names.get(i)).value(values.get(i)).build());
        }
        return fxDatas;
    }


    private List<String> filedNames(String key, Object value) {
        List<String> result = Lists.newArrayList();
        FxFiled fxFiled = value.getClass().getAnnotation(FxFiled.class);
        if (fxFiled != null && fxFiled.of().length > 0) {
            String[] fileds = fxFiled.of();
            for (String filed : fileds) {
                filed = key + "_" + filed;
                filed = filed.replaceAll("\\.", "_");
                result.add(filed);
            }
        } else {
            key = key.replaceAll("\\.", "_");
            result.add(key);
        }
        return result;
    }

    private List<Object> filedValues(Object value) {
        List<Object> result = Lists.newArrayList();
        FxFiled fxFiled = value.getClass().getAnnotation(FxFiled.class);
        if (fxFiled != null && fxFiled.of().length > 0) {
            String[] fileds = fxFiled.of();
            try {
                for (String filed : fileds) {
                    Object obj = PropertyUtils.getProperty(value, filed);
                    if (obj == null) {
                        String msg = MessageFormat.format("从对象[{0}]获取[{1}]字段的数据为空", value, filed);
                        throw new RuntimeException(msg);
                    }
                    result.add(obj);
                }
            } catch (Exception e) {
                String msg = MessageFormat.format("从对象[{0}]获取数据出错", value);
                throw new RuntimeException(msg, e);
            }
        } else {
            result.add(value.toString());
        }
        return result;
    }
}
