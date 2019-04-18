package com.newyu.utils.disruptor;

import com.google.common.base.Preconditions;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * ClassName: GroupProcess <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-16 下午4:42 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
public class GroupProcessor {
    private List<Processor> processors;

    public GroupProcessor add(Processor... processor) {
        Preconditions.checkNotNull(processors, "处理器不能为空");
        Arrays.stream(processor).forEach(x -> {
            this.processors.add(x);
        });
        return this;
    }

    public GroupProcessor add(List<Processor> processors) {
        Preconditions.checkNotNull(processors, "处理器不能为空");
        this.processors.addAll(processors);
        return this;
    }


}
