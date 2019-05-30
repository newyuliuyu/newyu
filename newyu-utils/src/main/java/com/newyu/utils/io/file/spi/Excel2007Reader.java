/**
 * Project Name:easydata.etl
 * File Name:XlsReader.java
 * Package Name:com.ez.etl.file.impl
 * Date:2017年3月14日下午1:46:20
 * Copyright (c) 2017, easytnt All Rights Reserved.
 */
package com.newyu.utils.io.file.spi;

import com.google.common.collect.Lists;
import com.newyu.utils.io.file.FileProcess;
import com.newyu.utils.io.file.HeaderMetadata;
import com.newyu.utils.io.file.Rowdata;
import com.newyu.utils.tool.FileUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

/**
 * ClassName: XlsReader <br/>
 * Function:  <br/>
 * Reason:  <br/>
 * date: 2017年3月14日 下午1:46:20 <br/>
 *
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class Excel2007Reader implements FileProcess {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Path filepath;

    private List<Excel2007Cell[]> dataset = Lists.newArrayList();
    private HeaderMetadata headerMetadata = new HeaderMetadata();
    private int curRowIdx = 0;

    public Excel2007Reader(Path filepath) {
        this.filepath = filepath;
        open();
    }

    private void open() {
        try {
            readData();
            int rowNum = dataset.size();
            headerMetadata.setTotalRow(rowNum);

            Excel2007Cell[] row = dataset.get(curRowIdx);
            for (Excel2007Cell value : row) {
                String v = value.getValue() == null ? "" : value.getValue();
                headerMetadata.addHeaderName(v, value.getColIdx());
            }
            curRowIdx += 1;
        } catch (Exception e) {
            throw new RuntimeException(String.format("打开xls文件%s出错", filepath), e);
        }
    }

    private void readData() throws Exception {

        OPCPackage pkg = OPCPackage.open(FileUtil.read(filepath.toString()));
        XSSFReader reader = new XSSFReader(pkg);
        SharedStringsTable sst = reader.getSharedStringsTable();

        Iterator<InputStream> ins = reader.getSheetsData();
        InputStream in = ins.next();
        InputSource sheetSource = new InputSource(in);

        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        SheetDataHandler handler = new SheetDataHandler(sst, new SetSheetData() {
            @Override
            public void set(Excel2007Cell[] data) {
                dataset.add(data);
            }
        }, 1);
        parser.setContentHandler(handler);
        try {
            parser.parse(sheetSource);
        } catch (Exception e) {
            throw e;
        } finally {
            in.close();
            pkg.close();
        }
    }

    @Override
    public HeaderMetadata getHeaderMetadata() {
        return headerMetadata;
    }

    @Override
    public boolean close() {
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ez.etl.Process#process(com.ez.etl.Rowdata)
     */
    @Override
    public Rowdata process(Rowdata rowdata) {

        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ez.etl.file.FileProcess#next()
     */
    @Override
    public boolean next() {
        return curRowIdx < headerMetadata.getTotalRow();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ez.etl.file.FileProcess#getRowdata()
     */
    @Override
    public Rowdata getRowdata() {
        Rowdata rowdata = new Rowdata();
        rowdata.setHeaderMetadata(headerMetadata);
        rowdata.setRowNum(curRowIdx);

        Excel2007Cell[] row = dataset.get(curRowIdx);
        for (Excel2007Cell value : row) {
            String v = value.getValue() == null ? "" : value.getValue();
            rowdata.addData(v, value.getColIdx());
        }

        curRowIdx++;
        return rowdata;
    }


}
