package com.newyu.utils.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * ClassName: ExcleTableBuilder <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-26 下午4:32 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ExcelTableBuilder {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private ExcelTable excelTable = new ExcelTable();

    public ExcelTable getExcelTable() {
        return excelTable;
    }

    public static ExcelTableBuilder instance() {
        return new ExcelTableBuilder();
    }

    public void builder(String savePath, Table... tables) {
        if (tables == null) {
            log.debug("table为null，不创建excel文件");
            return;
        }
        for (Table table : tables) {
            excelTable.addSheet(table.getName());
            createTable(excelTable, table, 0, 0);
        }
        try {
            excelTable.save(savePath);
        } catch (Exception e) {
            throw new RuntimeException(savePath, e);
        }

    }


    public static int createTable(ExcelTable excelTable, Table table, int beginRowIdx, int beginColunIdx) {

        CellStyle style = excelTable.getHeaderStyle();
        int rowIdx = beginRowIdx;
        for (Row row : table.getHeader()) {
            excelTable.resetRow(rowIdx);
            if (row.getHeight() > 0) {
                excelTable.getRow().setHeightInPoints(row.getHeight());
            }
            excelTable.appendCells(beginColunIdx, style, row.values());
            List<CellMergeInfo> cellMergeInfos = row.mergeInfo(rowIdx, beginColunIdx);
            for (CellMergeInfo mergeInfo : cellMergeInfos) {
                excelTable.mergeCells(mergeInfo.getBeginRow(),
                        mergeInfo.getEndRow(),
                        mergeInfo.getBeginCell(),
                        mergeInfo.getEndCell());
            }
            rowIdx++;
        }
        for (Row row : table.getBody()) {
            excelTable.resetRow(rowIdx);
            if (row.getHeight() > 0) {
                excelTable.getRow().setHeightInPoints(row.getHeight());
            }
            excelTable.appendCells(beginColunIdx, row.values());
            List<CellMergeInfo> cellMergeInfos = row.mergeInfo(rowIdx, beginColunIdx);
            for (CellMergeInfo mergeInfo : cellMergeInfos) {
                excelTable.mergeCells(mergeInfo.getBeginRow(),
                        mergeInfo.getEndRow(),
                        mergeInfo.getBeginCell(),
                        mergeInfo.getEndCell());
            }
            rowIdx++;
        }

        return rowIdx;
    }


}
