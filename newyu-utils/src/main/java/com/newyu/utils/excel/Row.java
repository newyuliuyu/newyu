package com.newyu.utils.excel;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * ClassName: Row <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-26 下午4:23 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class Row {
    private List<Cell> cells;
    private float height = 0;

    public float getHeight() {
        return height;
    }

    public Row setHeight(float height) {
        this.height = height;
        return this;
    }

    public Row add(Cell cell) {
        if (cells == null) {
            cells = Lists.newArrayList();
        }
        cells.add(cell);
        return this;
    }

    public Row add(Cell... cells) {
        if (cells != null) {
            for (Cell cell : cells) {
                add(cell);
            }
        }
        return this;
    }

    public Row add(Object... values) {
        if (values != null) {
            for (Object value : values) {
                if (value instanceof Cell) {
                    add((Cell) value);
                } else {
                    add(new Cell(value));
                }

            }
        }
        return this;
    }


    public Cell getCellOfIndex(int idx) {
        return cells.get(idx);
    }

    public int size() {
        return getCells().size();
    }

    public List<Cell> getCells() {
        if (cells == null) {
            return Lists.newArrayList();
        }
        return cells;
    }

    public Row setCells(List<Cell> cells) {
        this.cells = cells;
        return this;
    }

    public Object[] values() {
        Object[] result = new Object[cells.size()];
        int idx = 0;
        for (Cell cell : cells) {
            Object value = "";
            if (cell != null && cell.getValue() != null) {
                value = cell.getValue();
            }
            result[idx++] = value;
        }
        return result;
    }

    public List<CellMergeInfo> mergeInfo(int rowIdx) {
        return mergeInfo(rowIdx, 0);
    }

    public List<CellMergeInfo> mergeInfo(int rowIdx, int beginCol) {
        List<CellMergeInfo> cellMergeInfos = Lists.newArrayList();
        if (cells != null) {
            int colIdx = beginCol - 1;
            for (Cell cell : cells) {
                colIdx++;
                if (cell == null) {
                    continue;
                }
                if (cell.getMergeCol() > 0 || cell.getMergeRow() > 0) {
                    CellMergeInfo mergeInfo = new CellMergeInfo(rowIdx, colIdx);
                    cellMergeInfos.add(mergeInfo);
                    if (cell.getMergeCol() > 0) {
                        mergeInfo.setMergeCellNum(cell.getMergeCol());
                    }
                    if (cell.getMergeRow() > 0) {
                        mergeInfo.setMergeRowNum(cell.getMergeRow());
                    }
                }

            }
        }
        return cellMergeInfos;
    }
}
