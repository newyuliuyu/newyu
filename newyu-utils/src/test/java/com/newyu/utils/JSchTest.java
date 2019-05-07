package com.newyu.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.junit.Test;

import java.io.InputStream;

/**
 * ClassName: JSchTest <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-6 下午5:38 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class JSchTest {

    @Test
    public void test() throws Exception {
        JSch jsch = new JSch();
        Session sshSession = jsch.getSession("root", "192.168.1.252", 22);
        sshSession.setConfig("PreferredAuthentications", "password");
        sshSession.setConfig("StrictHostKeyChecking", "no");
        sshSession.setPassword("easyt&tnewa");
        sshSession.connect();

        ChannelExec channel = (ChannelExec) sshSession.openChannel("exec");
        channel.setCommand("if [ -f \"/root/liuyu/test/a.xls\" ]; then echo \"ddd\";else echo \"no\"; fi;");
//        channel.setInputStream(System.in);
//        channel.setOutputStream(System.out);

        InputStream inputStream = channel.getInputStream();
        channel.connect();
        StringBuilder sb = new StringBuilder();
        byte[] a=new byte[1024];
        while (inputStream.read(a)>0){
            sb.append(new String(a));
        }
        System.out.println(sb.toString().trim());


//        inputStream.read()

        //        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        channel.setOutputStream(outputStream);
//        channel.getInputStream();

//        System.out.println(outputStream.toString());


        channel.disconnect();
        sshSession.disconnect();

    }
}
