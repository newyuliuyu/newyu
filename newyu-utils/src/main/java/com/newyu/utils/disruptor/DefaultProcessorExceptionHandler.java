package com.newyu.utils.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import com.newyu.utils.exception.ExceptionToString;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * ClassName: DefaultProcessorExceptionHandler <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-16 下午5:12 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Slf4j
public class DefaultProcessorExceptionHandler implements ExceptionHandler<Event> {
    private DisruptorPerformer disruptorPerformer;

    public DefaultProcessorExceptionHandler(DisruptorPerformer disruptorPerformer) {
        this.disruptorPerformer = disruptorPerformer;
    }

    @Override
    public void handleEventException(Throwable ex, long sequence, Event event) {
        if (disruptorPerformer != null) {
            disruptorPerformer.getError().compareAndSet(false, true);
        }

        log.error("handle处理过程中出错了");
        String msg = MessageFormat.format("处理{0}数据出错", event.get().toString());
        log.error(msg);
        log.error(ExceptionToString.toString(ex));
        event.setError(true);
        event.setMessage(msg + ExceptionToString.formatHtml(ex));
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        if (disruptorPerformer != null) {
            disruptorPerformer.getError().compareAndSet(false, true);
        }
        log.error("启动handle出错!");
        log.error(ExceptionToString.toString(ex));
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        if (disruptorPerformer != null) {
            disruptorPerformer.getError().compareAndSet(false, true);
        }
        log.error("关闭handle出错!");
        log.error(ExceptionToString.toString(ex));
    }
}
