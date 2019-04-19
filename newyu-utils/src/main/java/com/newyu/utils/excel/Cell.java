package com.newyu.utils.excel;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * ClassName: Cell <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-26 下午4:23 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class Cell {
    private Object value;
    private int mergeRow;
    private int mergeCol;

    public Cell(Object vlaue) {
        this(vlaue, 0, 0);
    }

    public Cell(Object value, int mergeRow, int mergeCol) {
        this.value = value;
        this.mergeCol = mergeCol;
        this.mergeRow = mergeRow;

    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getMergeRow() {
        return mergeRow;
    }

    public void setMergeRow(int mergeRow) {
        this.mergeRow = mergeRow;
    }

    public int getMergeCol() {
        return mergeCol;
    }

    public void setMergeCol(int mergeCol) {
        this.mergeCol = mergeCol;
    }

    @Override
    public Cell clone() {
        return new Cell(value, mergeRow, mergeCol);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("mergeRow", mergeRow)
                .append("mergeCol", mergeCol)
                .toString();
    }
}
