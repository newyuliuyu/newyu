package com.newyu.utils.excel;

import lombok.Builder;
import lombok.Data;

/**
 * ClassName: Subtraction <br/>
 * Function:  减法. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-25 上午9:31 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
@Data
@Builder
public class Subtraction extends Cell implements Fun {
    private String filedName1;
    @Builder.Default
    private int filed1Idx = -1;
    private String filedName2;
    @Builder.Default
    private int filed2Idx = -1;

    public static void calculate(Table table, Row row, Subtraction subtraction) {
        int idx1 = subtraction.filed1Idx;
        if (idx1 == -1) {
            idx1 = table.getHeaderIdx(subtraction.filedName1);
        }
        int idx2 = subtraction.filed2Idx;
        if (idx2 == -1) {
            idx2 = table.getHeaderIdx(subtraction.filedName2);
        }
        double value1 = Double.parseDouble(row.getCellOfIndex(idx1).getValue().toString());
        double value2 = Double.parseDouble(row.getCellOfIndex(idx2).getValue().toString());
        subtraction.setValue(value1 - value2);
    }
}
