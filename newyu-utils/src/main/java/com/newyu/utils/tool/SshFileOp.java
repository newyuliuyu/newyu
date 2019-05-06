package com.newyu.utils.tool;

import com.newyu.utils.ssh.JschUtil;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;

/**
 * ClassName: SshFileUtil <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-6 下午2:53 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class SshFileOp implements FileOp {

    @Override
    public InputStream read(String filePath) {
        SshInfo sshInfo = parse(filePath);
        JschUtil jschUtil = JschUtil.newInstance(sshInfo.sshUrl);
        InputStream inputStream = null;
        try {
            ByteArrayOutputStream outputStream = jschUtil.dowloadFile(sshInfo.file);
            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            String msg = MessageFormat.format("读取文件{0}出错", filePath);
            throw new RuntimeException(msg, e);
        } finally {
            jschUtil.close();
        }
        return inputStream;
    }

    @Override
    public void save(InputStream filePath, String destination) {
        SshInfo sshInfo = parse(destination);
        JschUtil jschUtil = JschUtil.newInstance(sshInfo.sshUrl);
        try {
            notExistParentAndCreate(jschUtil, Paths.get(sshInfo.file));
            jschUtil.uploadFile(filePath, sshInfo.file);
        } catch (Exception e) {
            String msg = MessageFormat.format("保存文件到{0}出错", destination);
            throw new RuntimeException(msg, e);
        } finally {
            jschUtil.close();
        }
    }

    private SshInfo parse(String destination) {
        String prefix = destination.substring(0, 4);
        String sshUrl = destination.substring(4, destination.indexOf(":/"));
        String file = destination.substring(destination.indexOf(":/") + 1);
        SshInfo sshInfo = new SshInfo();
        sshInfo.prefix = prefix;
        sshInfo.sshUrl = sshUrl;
        sshInfo.file = file;
        return sshInfo;
    }

    private void notExistParentAndCreate(JschUtil jschUtil, Path path) {
        jschUtil.noExistMkdirs(path.getParent());
    }

    class SshInfo {
        private String prefix;
        private String sshUrl;
        private String file;
    }
}
