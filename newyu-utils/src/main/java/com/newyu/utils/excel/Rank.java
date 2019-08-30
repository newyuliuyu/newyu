package com.newyu.utils.excel;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ClassName: Rank <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-24 下午4:45 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Data
@Builder
public class Rank extends Cell implements Fun {
    private String rankFiledName;
    @Builder.Default
    private int rankFiledIdx = -1;
    @Builder.Default
    private String groupFiledName = "";
    @Builder.Default
    private int groupFiledIdx = -1;
    @Builder.Default
    private boolean desc = false;
    @Builder.Default
    private boolean calcluate = false;
    @Builder.Default
    private String filterFiledName = "";
    @Builder.Default
    private int filterFiledIdx = -1;
    @Builder.Default
    private List<String> filterVlaues = Lists.newArrayList();


    public static void calculate(Table table, Rank rank, int idx) {
        int rankIdx = rank.rankFiledIdx;
        if (rankIdx == -1) {
            rankIdx=table.getHeaderIdx(rank.rankFiledName);
        }
        int groupIdx = rank.groupFiledIdx;
        if (groupIdx == -1) {
            groupIdx = table.getHeaderIdx(rank.groupFiledName);
        }
        int filterIdx = rank.filterFiledIdx;
        if (filterIdx == -1) {
            filterIdx = table.getHeaderIdx(rank.filterFiledName);
        }
        List<Row> rows = Lists.newArrayList(table.getBody());

        if (filterIdx >= 0) {
            int tmpFilterIdx = filterIdx;
            rows = rows.stream().filter(x -> {
                String value = x.getCellOfIndex(tmpFilterIdx).getValue().toString();
                if (rank.filterVlaues.contains(value)) {
                    return false;
                }
                return true;
            }).collect(Collectors.toList());
        }


        Map<String, Integer> dataRank = RankHelper.dimRank(rows, groupIdx, rankIdx, true);
        for (Row x : table.getBody()) {
            Cell cell = x.getCellOfIndex(idx);
            if (cell instanceof Rank) {
                String key = x.getCellOfIndex(rankIdx).getValue().toString();
                if (groupIdx != -1) {
                    String value = x.getCellOfIndex(groupIdx).getValue().toString();
                    key += "." + value;
                }
                ((Rank) cell).setCalcluate(true);
                cell.setValue(dataRank.get(key));
            }
        }
    }
}
