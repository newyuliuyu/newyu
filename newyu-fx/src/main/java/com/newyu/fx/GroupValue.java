package com.newyu.fx;

import com.google.common.collect.Maps;
import com.newyu.domain.fx.GroupInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

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
@EqualsAndHashCode(of = {"valueMap"})
public class GroupValue {
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
}
