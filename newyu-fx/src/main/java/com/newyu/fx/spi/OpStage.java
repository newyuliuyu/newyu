package com.newyu.fx.spi;

import com.newyu.fx.Dataset;

/**
 * ClassName: OpStage <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-26 下午3:24 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public abstract class OpStage<T> extends DatasetImpl {
    private Dataset previousDataset;
    private Dataset nextDataset;

    public OpStage(Dataset<T> previousDataset) {
        this.previousDataset = previousDataset;
        if (previousDataset instanceof OpStage) {
            OpStage opStage = (OpStage) previousDataset;
            opStage.setNextDataset(this);
        }
    }

    public void setNextDataset(Dataset<T> nextDataset) {
        this.nextDataset = nextDataset;
    }

    public Dataset getPreviousDataset() {
        return previousDataset;
    }

    public Dataset getNextDataset() {
        return nextDataset;
    }

    public abstract Sink<T> opWrapSink(Sink downSink);
}
