package com.newyu.utils.disruptor;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * ClassName: ProcessorBuilder <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-16 下午4:39 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Getter
public class ProcessorBuilder {
    private List<GroupProcessor> groupProcessors = Lists.newArrayList();
    private Task task;

    private CleanProcess fail = () -> {
    };
    private CleanProcess success = () -> {
    };
    private CleanProcess finaly = () -> {
    };


    public void check() {
        Preconditions.checkArgument(!groupProcessors.isEmpty(), "处理中必须包含处理器");
        Preconditions.checkNotNull(task, "处理的任务不能为空");
    }

    public static ProcessorBuilder newInstance() {
        return new ProcessorBuilder();
    }

    public ProcessorBuilder addProcessor(Processor... processors) {
        Preconditions.checkNotNull(processors, "处理器不能为Null");
        groupProcessors.add(new GroupProcessor().add(processors));
        return this;
    }

    public ProcessorBuilder addPoolProcessor(Processor... processors) {
        Preconditions.checkNotNull(processors, "处理器不能为Null");
        groupProcessors.add(new PoolGroupProcessor().add(processors));
        return this;
    }

    public ProcessorBuilder fail(CleanProcess fail) {
        Preconditions.checkNotNull(fail, "处理器不能为Null");
        this.fail = fail;
        return this;
    }

    public ProcessorBuilder success(CleanProcess success) {
        Preconditions.checkNotNull(success, "处理器不能为Null");
        this.success = success;
        return this;
    }

    public ProcessorBuilder finaly(CleanProcess finaly) {
        Preconditions.checkNotNull(finaly, "处理器不能为Null");
        this.finaly = finaly;
        return this;
    }

    public ProcessorBuilder task(Task task) {
        this.task = task;
        return this;
    }


}
