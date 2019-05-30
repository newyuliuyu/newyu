package com.newyu.utils.tool;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
public class LocalFileOp implements FileOp {

    @Override
    public InputStream read(String filePath) {
        try {
            return new FileInputStream(filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(InputStream filePath, String destination) {
        try {
            Path path = Paths.get(destination);
            notExistParentAndCreate(path);
            FileUtils.copyInputStreamToFile(filePath, path.toFile());
        } catch (Exception e) {
            String msg = MessageFormat.format("保存文件到{0}出错", destination);
            throw new RuntimeException(msg, e);
        }
    }

    private void notExistParentAndCreate(Path path) {

        File file = path.getParent().toFile();
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public boolean existFile(String filePath) {
        Path path = Paths.get(filePath);
        return path.toFile().exists();
    }
}
