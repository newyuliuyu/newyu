package com.newyu.utils.excel;

import com.google.common.base.Function;
import com.google.common.collect.*;
import com.google.common.primitives.Doubles;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * ClassName: RankHelper <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-24 下午5:07 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class RankHelper {
    public static Map<String, Integer> dimRank(final List<Row> rows,
                                               final int groupIdx,
                                               final int sortIdx,
                                               final boolean desc) {
        if (groupIdx == -1) {
            return rank(rows, sortIdx, desc);
        }
        Multimap<String, Row> multimap = Multimaps.index(rows, new Function<Row, String>() {
            @Override
            public String apply(Row input) {
                return input.getCellOfIndex(groupIdx).getValue().toString();
            }
        });
        Map<String, Integer> examnnieRankMap = Maps.newHashMap();
        for (String key : multimap.keySet()) {
            List<Row> dimEses = Lists.newArrayList(multimap.get(key));
            Map<String, Integer> dimRank = rank(dimEses, sortIdx, desc);
            dimRank.entrySet().forEach(x -> {
                examnnieRankMap.put(x.getKey() + "." + key, x.getValue());
            });
        }
        return examnnieRankMap;
    }

    public static Map<String, Integer> rank(final List<Row> rows,
                                            final int sortIdx,
                                            final boolean desc) {
        Ordering<Row> myOrdering = Ordering.from(new Comparator<Row>() {
            @Override
            public int compare(Row o1, Row o2) {
                double score1 = Double.parseDouble(o1.getCellOfIndex(sortIdx).getValue().toString());
                double score2 = Double.parseDouble(o2.getCellOfIndex(sortIdx).getValue().toString());
                return desc ? Doubles.compare(score2, score1) : Doubles.compare(score1, score2);
            }
        });
        Collections.sort(rows, myOrdering);

        Map<String, Integer> examnnieRankMap = Maps.newHashMap();
        int rank = 1;
        int count = 0;
        Double preScore = Double.MAX_VALUE;
        for (Row o : rows) {
            String key = o.getCellOfIndex(sortIdx).getValue().toString();
            double score = Double.parseDouble(key);
            if (Doubles.compare(score, preScore) != 0) {
                preScore = score;
                rank = rank + count;
                count = 0;
            }
            examnnieRankMap.put(key, rank);
            count++;
        }
        return examnnieRankMap;
    }
}
