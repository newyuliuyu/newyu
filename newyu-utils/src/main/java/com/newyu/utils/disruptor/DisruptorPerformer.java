package com.newyu.utils.disruptor;

import com.google.common.base.Preconditions;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.Util;
import com.newyu.utils.exception.ExceptionToString;
import com.newyu.utils.thread.ThreadExecutor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ClassName: DisruptorPerformer <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-16 下午4:31 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class DisruptorPerformer implements Runnable {

    private Disruptor<Event> disruptor;
    private AtomicBoolean error = new AtomicBoolean(false);
    private AtomicBoolean warn = new AtomicBoolean(false);
    private LogManager logManager = new PrintLogLogManager(100);
    private ProcessorBuilder builder;
    private ExceptionHandler defaultExceptionHandler = new DefaultProcessorExceptionHandler(this);


    public void setDefaultExceptionHandler(ExceptionHandler defaultExceptionHandler) {
        this.defaultExceptionHandler = defaultExceptionHandler;
    }

    public void setLogManager(LogManager logManager) {
        this.logManager = logManager;
    }

    public void setProcessorBuilder(ProcessorBuilder processorBuilder) {
        this.builder = processorBuilder;
    }

    public AtomicBoolean getError() {
        return error;
    }

    public AtomicBoolean getWarn() {
        return warn;
    }

    @Override
    public void run() {
        process();
    }

    public void process() {
        Preconditions.checkNotNull(builder, "创建并行处理的构建器不能为空");
        builder.check();
        resetTotalTask();
        long b = System.currentTimeMillis();
        //初始化Disruptor
        initDisruptor();
        //设置默认的异常处理
        setDefaultExceptionHandler();
        //创建任务逻辑执行器
        EventHandlerGroup<Event> eventHandlerGroup = createHandler();
        //创建记录任务总数
        CountDownLatch counter = new CountDownLatch(builder.getTask().total());
        //设置任务执行器的减少
        counterHandle(eventHandlerGroup, counter);
        // 启动disruptor
        disruptor.start();
        //发送任务到disruptor
        startTask();

        //等待任务结束
        try {
            counter.await();
        } catch (Exception e) {
            log.error(ExceptionToString.toString(e));
        }
        // 所有任务处理完毕以后关闭
        disruptor.shutdown();

        if (error.get()) {
            //分析出错清理
            builder.getFail().clean();
        } else {
            //分析成功需要的后续处理
            builder.getSuccess().clean();
        }
        //无论分析是否成功都必须清理的任务
        builder.getFinaly().clean();
        long e = System.currentTimeMillis();
        log.debug("花费时间:{}s", (e - b) * 1.0 / 1000);
    }

    private void startTask() {
        int idx = 1;
        Task task = builder.getTask();
        while (task.next()) {
            Object data = task.get();
            Event event = new Event();
            event.setData(data);
            event.setRowIdx(idx++);
            disruptor.publishEvent(new DefaultEventTranslator(event));
        }
    }

    private void resetTotalTask() {
        if (logManager != null) {
            logManager.restCache(builder.getTask().total(), "");
        }
    }

    private void initDisruptor() {
        disruptor = new Disruptor(DefaultEventFactory.factory(),
                Util.ceilingNextPowerOfTwo(1024),
                ThreadExecutor.createThreadFactory(),
                ProducerType.SINGLE,
                new BlockingWaitStrategy());
    }

    private void setDefaultExceptionHandler() {
        disruptor.setDefaultExceptionHandler(defaultExceptionHandler);
    }

    private EventHandlerGroup<Event> createHandler() {
        EventHandlerGroup<Event> eventHandlerGroup = null;
        List<GroupProcessor> groupProcessors = builder.getGroupProcessors();
        for (GroupProcessor groupProcessor : groupProcessors) {
            List<Processor> processors = groupProcessor.getProcessors();
            if (groupProcessor instanceof PoolGroupProcessor) {
                eventHandlerGroup = createHandleEventsWithWorkerPool(eventHandlerGroup, processors);
            } else {
                eventHandlerGroup = createHandleEventsWith(eventHandlerGroup, processors);
            }
        }
        return eventHandlerGroup;
    }

    private EventHandlerGroup<Event> createHandleEventsWithWorkerPool(EventHandlerGroup<Event> eventHandlerGroup,
                                                                      List<Processor> processors) {
        if (eventHandlerGroup == null) {
            return disruptor.handleEventsWithWorkerPool(toArray(processors));
        } else {
            return eventHandlerGroup.handleEventsWithWorkerPool(toArray(processors));
        }
    }

    private EventHandlerGroup<Event> createHandleEventsWith(EventHandlerGroup<Event> eventHandlerGroup,
                                                            List<Processor> processors) {
        if (eventHandlerGroup == null) {
            return disruptor.handleEventsWith(toArray(processors));
        } else {
            return eventHandlerGroup.handleEventsWith(toArray(processors));
        }
    }

    private ProcessorHandler[] toArray(List<Processor> processors) {
        ProcessorHandler[] processorHandlers = new ProcessorHandler[processors.size()];
        for (int i = 0; i < processors.size(); i++) {
            processorHandlers[i] = new ProcessorHandler(processors.get(i));
        }
        return processorHandlers;
    }

    private void recordLog(Event event) {
        if (logManager != null) {
            logManager.increaseLog("");
            if (event.isError()) {
                logManager.error(event.getMessage());
            }
            if (event.isWarn()) {
                logManager.warn(event.getMessage());
            }
        }
    }

    private void counterHandle(EventHandlerGroup<Event> eventHandlerGroup, final CountDownLatch counter) {
        ProcessorHandler end = new ProcessorHandler(new Processor<Event>() {
            @Override
            public Class<Event> getDataClass() {
                return Event.class;
            }

            @Override
            public void process(Event event) {
                counter.countDown();

                log.debug("已完量/未完成量：{}/{}", event.getRowIdx(), counter.getCount());
                log.debug("已完成:" + event.get().toString());


                if (event.isError()) {
                    error.compareAndSet(false, true);
                }
                if (event.isWarn()) {
                    warn.compareAndSet(false, true);
                }
                recordLog(event);
            }
        });

        if (eventHandlerGroup == null) {
            disruptor.handleEventsWith(end);
        } else {
            eventHandlerGroup.handleEventsWith(end);
        }
    }
}
