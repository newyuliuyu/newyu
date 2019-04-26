package com.newyu.fx.spi;

/**
 * ClassName: ChainSink <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-26 下午4:17 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ChainSink<T> implements Sink<T> {
    protected Sink downSink;

    public ChainSink(Sink downSink) {
        this.downSink = downSink;
    }

    @Override
    public void begin(int size) {
        downSink.begin(size);
    }

    @Override
    public void end() {
        downSink.end();
    }
}
