package com.newyu.utils.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * ClassName: ProcessorHandler <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-17 下午4:49 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ProcessorHandler implements EventHandler<Event>, WorkHandler<Event> {

    private Processor processor;

    @SuppressWarnings("rawtypes")
    public ProcessorHandler(Processor processor) {
        this.processor = processor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onEvent(Event event) throws Exception {
//        这里是计数任务处理的个数的一个执行器
        if (Event.class.equals(processor.getDataClass())) {
            processor.process(event);
        } else {
            if (event.isError()) {
                return;
            }
            processor.process(event.get());
        }
    }

    @Override
    public void onEvent(Event event, long sequence, boolean endOfBatch) throws Exception {
        onEvent(event);
    }
}
