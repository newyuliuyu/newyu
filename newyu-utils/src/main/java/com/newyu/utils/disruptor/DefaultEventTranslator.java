package com.newyu.utils.disruptor;

import com.lmax.disruptor.EventTranslator;

/**
 * ClassName: EventTranslator <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-17 下午5:10 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class DefaultEventTranslator implements EventTranslator<Event> {
    private Event event;

    public DefaultEventTranslator(Event event) {
        this.event = event;
    }

    @Override
    public void translateTo(Event event, long sequence) {
        event.copyFrom(this.event);
    }
}
