/**
 * Project Name:easydata.etl
 * File Name:RowData.java
 * Package Name:com.ez.etl
 * Date:2017年3月14日上午11:02:00
 * Copyright (c) 2017, easytnt All Rights Reserved.
 */
package com.newyu.utils.io.file;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ClassName: RowData <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2017年3月14日 上午11:02:00 <br/>
 *
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class Rowdata {
    private int rowNum;
    private HeaderMetadata headerMetadata;
    private Map<Integer, String> rowdata = Maps.newHashMap();

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public HeaderMetadata getHeaderMetadata() {
        return headerMetadata;
    }

    public void setHeaderMetadata(HeaderMetadata headerMetadata) {
        this.headerMetadata = headerMetadata;
    }


    public Rowdata addData(String value, int idx) {
        rowdata.put(idx, value.trim());
        return this;
    }

    public Rowdata addData(String headerName, String value) {
        int idx = headerMetadata.getHeaderNameIndx(headerName);
        if (idx != -1) {
            addData(value, idx);
        }
        return this;
    }

    public String getData(String... headerNames) {
        int idx = -1;
        for (String headerName : headerNames) {
            idx = headerMetadata.getHeaderNameIndx(headerName);
            if (idx != -1) {
                break;
            }
        }
        Optional<String> value = Optional.empty();
        if (idx != -1) {
            value = Optional.ofNullable(rowdata.get(idx));
        }
        return value.orElse("");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<String> names = headerMetadata.getHeaderNames();
        for (String name : names) {
            String value = getData(name);
            sb.append(value).append(",");
        }
        return new ToStringBuilder(this).append("rowIdx", rowNum).append("rowdata", sb.toString())
                .append("headers", headerMetadata.toString()).build();
    }

}
