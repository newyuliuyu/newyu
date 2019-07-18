package com.newyu.utils.io.file.spi;

import com.newyu.utils.io.file.Rowdata;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * ClassName: Excel2007ReaderTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-7-9 下午2:41 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class Excel2007ReaderTest {
    private Path rootPath = Paths.get("/home/liuyu/test/test-data");

    @Test
    public void testSheetName() throws Exception {
        Path path = rootPath.resolve("1.xlsx");
        Excel2007Reader excelReader = new Excel2007Reader(path);

        List<String> sheetNames = excelReader.getSheetNames();
        sheetNames.forEach(System.out::println);
        sheetNames.forEach(x -> getData(excelReader, x));
//        for(String s:sheetNames){
//            getData(excelReader, s);
//        }

        excelReader.close();

    }

    private void getData(Excel2007Reader excelReader, String sheetName) {
        System.out.println(sheetName + "=================");
        excelReader.changeSheet(sheetName);
        while (excelReader.next()) {
            Rowdata rowdata = excelReader.getRowdata();
            System.out.println(rowdata);
        }
    }

}