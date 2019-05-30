package com.newyu.fx;

/**
 * ClassName: MyDataset <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-20 上午10:15 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class MyDataset implements MeDataset<Double> {
    private Double[] d1 = new Double[]{1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d, 9d};
    private Double[] d2 = new Double[]{10d, 11d, 12d, 13d, 14d, 15d, 16d, 17d, 18d, 19d};
    private Double[] d3 = new Double[]{20d, 21d, 22d, 23d, 24d, 25d, 26d, 27d, 28d, 29d};
    private int i = -1;

    public boolean hasNext() {
        return ++i < 3;
    }

    public Double[] data() {
        if (i == 0) {
            return d1;
        } else if (i == 1) {
            return d2;
        } else if (i == 2) {
            return d3;
        }
        return null;
    }
}
