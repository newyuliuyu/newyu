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

    public Table addHeader(Row row) {
        if (header == null) {
            header = Lists.newArrayList();
        }
        header.add(row);
        return this;
    }

    public Table setHeader(int idx, Row row) {
        if (header == null) {
            header = Lists.newArrayList();
        }
        header.add(idx, row);
        return this;
    }

    public Table addHeader(List<Row> rows) {
        if (header == null) {
            header = Lists.newArrayList();
        }
        header.addAll(rows);
        return this;
    }

    public Table addBody(Row row) {
        if (body == null) {
            body = Lists.newArrayList();
        }
        body.add(row);
        return this;
    }

    public Table addBody(List<Row> rows) {
        if (body == null) {
            body = Lists.newArrayList();
        }
        body.addAll(rows);
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
        this.header = header;
    }

    public List<Row> getBody() {
        if (body == null) {
            return Lists.newArrayList();
        }
        return body;
    }

    public void setBody(List<Row> body) {
        this.body = body;
    }
}
