package com.newyu.utils.disruptor;

import com.newyu.utils.cache.MemoryCache;
import com.newyu.utils.progress.ProgressEvent;
import com.newyu.utils.progress.Progresses;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName: WebLogManager <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-17 上午9:25 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class WebLogManager implements LogManager {
    private String key;
    private Progresses progresses;
    private Lock lock = new ReentrantLock();
    private AtomicInteger complete = new AtomicInteger(0);
    private int totalNum = 0;

    public WebLogManager(String key, int totalNum) {
        this.key = key;
        progresses = new Progresses(true);
        MemoryCache.instance().put(key, progresses);
    }

    private void increase() {
        complete.incrementAndGet();
    }

    private void increase(int complate) {
        complete.set(complate);
    }

    @Override
    public void setFinished(String message) {
        ProgressEvent event = new ProgressEvent(100, 100, message, true);
        progresses.publish(event);
    }

    @Override
    public void restCache(int totalNum, String message) {
        this.totalNum = totalNum;
    }

    @Override
    public void increaseLog(String message) {
        increase();
        log(message);
    }

    @Override
    public void increaseLog(String messageFormat, Object... args) {
        increase();
        log(messageFormat, args);
    }

    @Override
    public void log(int complate, String message) {
        increase(complate);
        log(message);
    }

    @Override
    public void log(int complate, String messageFormat, Object... args) {
        increase(complate);
        log(messageFormat, args);
    }

    @Override
    public void log(String message) {
        ProgressEvent event = new ProgressEvent(complete.get(), totalNum, message);
        progresses.publish(event);
    }

    @Override
    public void log(String messageFomart, Object... args) {
        String message = MessageFormat.format(messageFomart, args);
        this.log(message);
    }

    @Override
    public void error(String message) {
        ProgressEvent event = new ProgressEvent(complete.get(), totalNum, message);
        event.setMsgType(1);
        progresses.publish(event);
    }

    @Override
    public void warn(String message) {
        ProgressEvent event = new ProgressEvent(complete.get(), totalNum, message);
        event.setMsgType(2);
        progresses.publish(event);
    }
}
