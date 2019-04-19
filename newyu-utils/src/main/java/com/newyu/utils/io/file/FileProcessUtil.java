/**
 * Project Name:easydata.etl
 * File Name:FileProcessUtil.java
 * Package Name:com.ez.etl.util
 * Date:2017年3月15日下午6:13:21
 * Copyright (c) 2017, easytnt All Rights Reserved.
 */
package com.newyu.utils.io.file;

import com.newyu.utils.io.file.spi.CsvReader;
import com.newyu.utils.io.file.spi.DbfReader;
import com.newyu.utils.io.file.spi.Excel2007Reader;
import com.newyu.utils.io.file.spi.ExcelReader;
import com.newyu.utils.tool.FileUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ClassName: FileProcessUtil <br/>
 * Function:  <br/>
 * Reason: <br/>
 * date: 2017年3月15日 下午6:13:21 <br/>
 *
 * @author 刘海林
 * @version v1.0
 * @since JDK 1.7+
 */
public class FileProcessUtil {
    private static final String XLS = ".xls";
    private static final String XLSX = ".xlsx";
    private static final String DBF = ".dbf";
    private static final String CSV = ".csv";
    private static final String TXT = ".txt";

    public static FileProcess getFileProcess(String filepath) {
        Path path = Paths.get(filepath);
        return getFileProcess(path);
    }

    public static FileProcess getFileProcess(Path filepath) {
        String suffix = FileUtil.fileSuffix(filepath).toLowerCase();
        if (XLS.equals(suffix)) {
            return new ExcelReader(filepath);
        } else if (XLSX.equals(suffix)) {
            return new Excel2007Reader(filepath);
        } else if (DBF.equals(suffix)) {
            return new DbfReader(filepath);
        } else if (CSV.equals(suffix) || CSV.equals(suffix)) {
            return new CsvReader(filepath);
        } else {
            throw new RuntimeException(String.format("没有找到处理%s文件的处理器", filepath));
        }
    }

    public static String getPrefix(String filepath) {
        Path path = Paths.get(filepath);
        String fileName = path.getFileName().toString();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return prefix;
    }
}
