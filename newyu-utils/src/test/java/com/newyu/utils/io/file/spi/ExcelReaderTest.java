package com.newyu.utils.io.file.spi;

import com.newyu.utils.io.file.Rowdata;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * ClassName: ExcelReaderTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-9 下午2:01 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ExcelReaderTest {
    private Path rootPath = Paths.get("/home/liuyu/test/test-data");

    @Test
    public void testSheetName() throws Exception {
        Path path = rootPath.resolve("2.xls");
        ExcelReader excelReader = new ExcelReader(path);

        List<String> sheetNames = excelReader.getSheetNames();
        sheetNames.forEach(System.out::println);
        sheetNames.forEach(x -> getData(excelReader, x));

        excelReader.close();

    }

    private void getData(ExcelReader excelReader, String sheetName) {
        System.out.println(sheetName + "=================");
        excelReader.changeSheet(sheetName);
        while (excelReader.next()) {
            Rowdata rowdata = excelReader.getRowdata();
            System.out.println(rowdata);
        }
    }

}