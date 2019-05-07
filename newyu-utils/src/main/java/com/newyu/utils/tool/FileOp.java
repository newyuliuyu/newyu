package com.newyu.utils.tool;

import java.io.InputStream;

/**
 * ClassName: FileOp <br/>
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(可选). <br/>
 * date: 19-5-6 下午2:55 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public interface FileOp {

    InputStream read(final String filePath);

    void save(final InputStream filePath, final String destination);

    boolean existFile(String filePath);

    public static boolean isSSHFile(String filePath) {
        filePath = filePath.toLowerCase();
        return filePath.startsWith("ssh:");
    }
}
