package com.newyu.utils.disruptor;

import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.concurrent.atomic.AtomicInteger;

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
@Slf4j
public class PrintLogLogManager implements LogManager {
    private AtomicInteger complete = new AtomicInteger(0);
    private int totalNum = 0;

    public PrintLogLogManager(int totalNum) {
        this.totalNum = totalNum;
        log.debug("创建进度，总数{}", totalNum);
    }

    private void increase() {
        complete.incrementAndGet();
    }

    private void increase(int complate) {
        complete.set(complate);
    }

    @Override
    public void setFinished(String message) {
        log.debug("完成进度:{}，消息：{},状态:{}", totalNum, message, true);
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
        log.debug("log:完成进度:{}/{}，消息：{},状态:{}", complete.get(), totalNum, message, false);
    }

    @Override
    public void log(String messageFomart, Object... args) {
        String message = MessageFormat.format(messageFomart, args);
        this.log(message);
    }

    @Override
    public void error(String message) {
        log.debug("error:完成进度:{}/{}，消息：{},状态:{}", complete.get(), totalNum, message, false);
    }

    @Override
    public void warn(String message) {
        log.debug("warn:完成进度:{}/{}，消息：{},状态:{}", complete.get(), totalNum, message, false);
    }
}
