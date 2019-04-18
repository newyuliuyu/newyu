package com.newyu.utils.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * ClassName: EventFactory <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-17 下午4:25 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class DefaultEventFactory implements EventFactory<Event> {

    public static DefaultEventFactory factory() {
        return new DefaultEventFactory();
    }

    @Override
    public Event newInstance() {
        return new Event();
    }
}
