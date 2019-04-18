package com.newyu.utils.disruptor;

/**
 * ClassName: LogManager <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-16 下午5:34 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface LogManager {
    void log(String message);

    void error(String message);

    void warn(String message);

    void log(String messageFomart, Object... args);

    void restCache(int totalNum, String message);

    void setFinished(String message);

    void increaseLog(String message);

    void increaseLog(String messageFormat, Object... args);

    void log(int complate, String message);

    void log(int complate, String messageFormat, Object... args);
}
