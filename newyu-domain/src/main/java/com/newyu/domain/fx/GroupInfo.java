package com.newyu.domain.fx;

import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * ClassName: GroupInfo <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-15 下午5:30 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"name", "groupFileds", "saveTo"})
public class GroupInfo {
    private String name;
    private List<String> groupFileds = Lists.newArrayList();
    private String saveTo;

    public GroupInfo add(String filed) {
        groupFileds.add(filed);
        return this;
    }
}
