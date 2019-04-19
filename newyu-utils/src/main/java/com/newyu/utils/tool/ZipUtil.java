package com.newyu.utils.tool;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ClassName: ZipUtil2 <br/>
 * Function: . <br/>
 * Reason: . <br/>
 * date: 17-11-28 上午10:28 <br/>
 *
 * @author liuyu
 * @version v1.0
 * @since JDK 1.7+
 */
public class ZipUtil {
    private ZipUtil() {
    }

    public static void doCompress(String srcFile, String zipFile, FileFilter fileFilter) throws IOException {
        doCompress(new File(srcFile), new File(zipFile), fileFilter);
    }

    public static void doCompress(String srcFile, String zipFile) throws IOException {
        doCompress(new File(srcFile), new File(zipFile), null);
    }

    /**
     * 文件压缩
     *
     * @param srcFile 目录或者单个文件
     * @param zipFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File zipFile, FileFilter fileFilter) throws IOException {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            doCompress(srcFile, out, fileFilter);
        } catch (Exception e) {
            throw e;
        } finally {
            out.close();//记得关闭资源
        }
    }

    public static void doCompress(String filelName, ZipOutputStream out, FileFilter fileFilter) throws IOException {
        doCompress(new File(filelName), out, fileFilter);
    }

    public static void doCompress(File file, ZipOutputStream out, FileFilter fileFilter) throws IOException {
        doCompress(file, out, "", fileFilter);
    }

    public static void doCompress(File inFile, ZipOutputStream out, String dir, FileFilter fileFilter) throws IOException {
        if (inFile.isDirectory()) {
            File[] files = null;
            if (fileFilter != null) {
                files = inFile.listFiles(fileFilter);
            } else {
                files = inFile.listFiles();
            }
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir + "/" + name;
                    }
                    ZipUtil.doCompress(file, out, name, fileFilter);
                }
            }
        } else {
            ZipUtil.doZip(inFile, out, dir);
        }
    }

    public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
        String entryName = null;
        if (!"".equals(dir)) {
            entryName = dir + "/" + inFile.getName();
        } else {
            entryName = inFile.getName();
        }
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);

        int len = 0;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(inFile);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }
}
