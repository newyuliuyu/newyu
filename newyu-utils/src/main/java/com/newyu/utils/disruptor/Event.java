package com.newyu.utils.disruptor;

import lombok.*;

/**
 * ClassName: Event <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-16 下午4:58 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Event {
    private Object data;
    private boolean error = false;
    private boolean warn = false;
    private String message;
    private int rowIdx = 0;

    public <T> T get() {
        return (T) data;
    }

    public void copyFrom(Event from) {
        data = from.data;
        rowIdx = from.rowIdx;
        error = false;
        error = false;
        message = "";
    }
}
