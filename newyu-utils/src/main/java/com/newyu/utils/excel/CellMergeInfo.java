package com.newyu.utils.excel;

/**
 * ClassName: CellMergeInfo <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-26 下午4:40 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class CellMergeInfo {
    private int beginRow;
    private int endRow;
    private int beginCell;
    private int endCell;

    public CellMergeInfo(int beginRow, int beginCell) {
        this.beginRow = this.endRow = beginRow;
        this.beginCell = this.endCell = beginCell;
    }

    public int getBeginRow() {
        return beginRow;
    }

    public void setBeginRow(int beginRow) {
        this.beginRow = beginRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public void setMergeRowNum(int num) {
        setEndRow(num + beginRow);
    }

    public int getBeginCell() {
        return beginCell;
    }

    public void setBeginCell(int beginCell) {
        this.beginCell = beginCell;
    }

    public int getEndCell() {
        return endCell;
    }

    public void setEndCell(int endCell) {
        this.endCell = endCell;
    }

    public void setMergeCellNum(int endCell) {
        setEndCell(endCell + beginCell);
    }
}
