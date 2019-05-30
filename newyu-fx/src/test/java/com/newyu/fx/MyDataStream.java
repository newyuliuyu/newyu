package com.newyu.fx;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * ClassName: MyData <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-20 上午10:08 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class MyDataStream<T> {

    public Stream<Double> stream(MeDataset<T> dataset) {
        return StreamSupport.stream(new MyIteratorSpliterator(dataset,0), false);
    }


    static class MyIteratorSpliterator<T> implements Spliterator<T> {

        private final int characteristics;
        private MeDataset<T> dataset;

        public MyIteratorSpliterator(MeDataset<T> dataset, int characteristics) {
            this.dataset = dataset;
            this.characteristics = (characteristics & Spliterator.CONCURRENT) == 0
                    ? characteristics | Spliterator.SIZED | Spliterator.SUBSIZED
                    : characteristics;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            return null;
        }

        @Override
        public long estimateSize() {
            return 0;
        }

        @Override
        public int characteristics() {
            return 0;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            while (dataset.hasNext()) {
                T[] data = dataset.data();
                for (T d : data) {
                    action.accept(d);
                }
            }
        }

        @Override
        public long getExactSizeIfKnown() {
            return 0;
        }

        @Override
        public boolean hasCharacteristics(int characteristics) {
            return false;
        }

        @Override
        public Comparator<? super T> getComparator() {
            return null;
        }
    }
}
