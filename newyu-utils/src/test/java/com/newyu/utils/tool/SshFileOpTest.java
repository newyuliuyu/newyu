package com.newyu.utils.tool;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ClassName: SshFileOpTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-6 下午3:19 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class SshFileOpTest {

    @Test
    public void test() throws Exception {
//        String source = "ssh:root@192.168.1.252:22:easyt&tnewa:/root/liuyu/test";
//        String prefix = source.substring(0, 4);
//        System.out.println(prefix);
//        String sshUrl = source.substring(4, source.indexOf(":/"));
//        System.out.println(sshUrl);
//        String addr = source.substring(source.indexOf(":/") + 1);
//        System.out.println(addr);

        String source = "ssh:root@192.168.1.252:22:easyt&tnewa:/root/liuyu/test";
        Path path = Paths.get(source);
        path = path.resolve("a.xls");
        System.out.println(path.toString());
    }

    @Test
    public void saveTest() throws Exception {

        FileInputStream in = new FileInputStream(new File("/home/liuyu/tmp/excle/a.xls"));
        String source = "ssh:root@192.168.1.252:22:easyt&tnewa:/root/liuyu/test/a.xls";

        SshFileOp sshFileOp = new SshFileOp();
        sshFileOp.save(in, source);
    }

    @Test
    public void open() throws Exception {

//        FileInputStream in = new FileInputStream(new File("/home/liuyu/tmp/excle/a.xls"));
        String source = "ssh:root@192.168.1.252:22:easyt&tnewa:/root/liuyu/test/a22.xls";

        SshFileOp sshFileOp = new SshFileOp();
        InputStream inputStream = sshFileOp.read(source);

        LocalFileOp localFileOp = new LocalFileOp();
        localFileOp.save(inputStream, "/home/liuyu/tmp/excle/a2.xls");
    }
    @Test
    public void existFile() throws Exception {

        boolean k = Boolean.parseBoolean("true");
        System.out.println();

//        FileInputStream in = new FileInputStream(new File("/home/liuyu/tmp/excle/a.xls"));
        String source = "ssh:root@192.168.1.252:22:easyt&tnewa:/root/liuyu/test/a.xls";

        SshFileOp sshFileOp = new SshFileOp();
       boolean a =  sshFileOp.existFile(source);
       System.out.println();
    }

}