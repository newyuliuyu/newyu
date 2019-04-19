package com.newyu.utils.progress;

import java.util.List;

/**
 * ClassName: ProgressListener <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 17-11-27 上午11:26 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@FunctionalInterface
public interface ProgressListener {
    void listener(List<ProgressEvent> events, boolean isOver);
}
