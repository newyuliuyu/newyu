/**
 * Project Name:easydata.etl
 * File Name:DBFReader.java
 * Package Name:com.ez.etl.file.impl
 * Date:2017年3月14日下午6:27:55
 * Copyright (c) 2017, easytnt All Rights Reserved.
 */
package com.newyu.utils.io.file.spi;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.newyu.utils.io.file.FileProcess;
import com.newyu.utils.io.file.HeaderMetadata;
import com.newyu.utils.io.file.Rowdata;
import com.newyu.utils.tool.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Path;

/**
 * ClassName: DBFReader <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2017年3月14日 下午6:27:55 <br/>
 *
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class DbfReader implements FileProcess {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Path filepath;
    private InputStream in;
    private DBFReader reader;
    private HeaderMetadata headerMetadata = new HeaderMetadata();
    private Object[] curRowValues;
    private int rowIdx = 1;

    public DbfReader(Path filepath) {
        this.filepath = filepath;
        init();
    }

    private void init() {
        try {
//            in = new FileInputStream(filepath.toFile());
            in = FileUtil.read(filepath.toString());
            reader = new DBFReader(in);
            reader.setCharactersetName("GBK");

            int totalRowNum = reader.getRecordCount();
            headerMetadata.setTotalRow(totalRowNum);
            int fieldsCount = reader.getFieldCount();
            // 取出字段信息
            for (int i = 0; i < fieldsCount; i++) {
                DBFField field = reader.getField(i);

                headerMetadata.addHeaderName(field.getName().trim(), i);
            }

        } catch (Exception e) {
            throw new RuntimeException(String.format("打开dbf文件%s出错", filepath), e);
        }
    }

    @Override
    public Rowdata process(Rowdata rowdata) {
        return null;
    }

    @Override
    public HeaderMetadata getHeaderMetadata() {
        return headerMetadata;
    }

    @Override
    public boolean next() {
        try {
            curRowValues = reader.nextRecord();
        } catch (Exception e) {
            throw new RuntimeException(String.format("读取dbf文件%s内容出错", filepath), e);
        }
        return curRowValues != null;
    }

    @Override
    public Rowdata getRowdata() {
        Rowdata rowdata = new Rowdata();
        rowdata.setRowNum(rowIdx);
        rowdata.setHeaderMetadata(headerMetadata);
        for (int i = 0; i < curRowValues.length; i++) {
            rowdata.addData(curRowValues[i].toString().trim(), i);
        }
        rowIdx++;
        return rowdata;
    }

    @Override
    public boolean close() {
        try {
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("关闭dbf文件%s出错", filepath), e);
        }
        return true;
    }

}
