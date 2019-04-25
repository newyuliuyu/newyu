package com.newyu.fx;

import com.newyu.fx.spi.GroupDatasetImpl;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ClassName: PathTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-4-24 下午4:34 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class PathTest {

    @Test
    public void test() throws Exception {
        Path path = Paths.get("/home/liuyu/tmp/excle");

        System.out.println(path.resolve("b.xls"));
        System.out.println(path.resolveSibling("b.xls"));
        System.out.println(path.relativize(Paths.get("/home")));
    }
    @Test
    public void test2() throws Exception {
        Dataset path = new GroupDatasetImpl();

        System.out.println(path instanceof GroupDataset);
        System.out.println(path instanceof Dataset);
    }

}
