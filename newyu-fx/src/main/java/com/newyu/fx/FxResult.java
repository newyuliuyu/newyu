package com.newyu.fx;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * ClassName: FxResult <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-15 下午5:33 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
@Setter
public class FxResult {

    private GroupValue groupValue;
    private Map<String, FxData> fxDataMap = Maps.newHashMap();

    public FxResult add(FxData fxData) {
        fxDataMap.put(fxData.getName(), fxData);
        return this;
    }

    public List<FxData> toList() {
        return Lists.newArrayList(fxDataMap.values());
    }
}
