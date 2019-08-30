package com.newyu.utils.excel;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * ClassName: Table <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-3-26 下午4:22 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class Table {
    private String name = "test";
    private List<Row> header;
    private List<Row> body;


    public int getHeaderIdx(String headerName) {
        for (Row row : header) {
            List<Cell> cells = row.getCells();
            int idx = 0;
            for (Cell cell : cells) {
                if (cell.getValue().toString().equals(headerName)) {
                    return idx;
                }
                idx++;
            }
        }
        return -1;
    }

    public Table addHeader(Row row) {
        if (header == null) {
            header = Lists.newArrayList();
        }
        row.setTable(this);
        header.add(row);
        return this;
    }

    public Table setHeader(int idx, Row row) {
        if (header == null) {
            header = Lists.newArrayList();
        }
        row.setTable(this);
        header.add(idx, row);
        return this;
    }

    public Table addHeader(List<Row> rows) {
        rows.forEach(x -> addHeader(x));
        return this;
    }

    public Table addBody(Row row) {
        if (body == null) {
            body = Lists.newArrayList();
        }
        row.setTable(this);
        body.add(row);
        return this;
    }

    public Table addBody(List<Row> rows) {
        rows.forEach(x -> addBody(x));
        return this;
    }

    public int rows() {
        return getHeader().size() + getBody().size();
    }

    public int cols() {
        if (header != null && header.size() > 0) {
            return header.get(0).size();
        } else if (body != null && body.size() > 0) {
            return body.get(0).size();
        } else {
            return 0;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Row> getHeader() {
        if (header == null) {
            return Lists.newArrayList();
        }
        return header;
    }

    public void setHeader(List<Row> header) {
        this.header = Lists.newArrayList();
        header.forEach(x -> addHeader(x));
    }

    public List<Row> getBody() {
        if (body == null) {
            return Lists.newArrayList();
        }
        return body;
    }

    public void setBody(List<Row> body) {
        this.body = Lists.newArrayList();
        body.forEach(x -> addBody(x));
    }
}
