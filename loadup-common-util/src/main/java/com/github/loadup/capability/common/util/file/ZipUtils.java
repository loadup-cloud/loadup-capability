package com.github.loadup.capability.common.util.file;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

/**
 * <p>
 * 文件与ZIP文件之间的转换
 * </p>
 */

public class ZipUtils {

    final public static String ZIP_FORMAT = "zip";

    final public static String SYMBOL_POINT = ".";

    final public static String SYMBOL_SLASH = "/";

    private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * 将byte数组压缩
     *
     * @param datas
     * @return
     */
    public static void zipBytes(byte[][] datas, String[] fileNames, OutputStream outputStream) {
        if (datas == null) {
            return;
        }
        InputStream[] osAry = new InputStream[datas.length];
        int i = 0;
        for (byte[] data : datas) {
            osAry[i++] = new ByteArrayInputStream(data);
        }

        zipStreams(osAry, fileNames, outputStream);

    }

    public static void zipStreams(InputStream[] inputStreams, String[] fileNames,
            OutputStream outputStream) {

        ZipOutputStream out = new ZipOutputStream(outputStream);
        try {
            // 创建ZIP文件输出流

            // 压缩文件
            int i = 0;
            for (InputStream is : inputStreams) {
                // 添加 ZIP entry 到 ZIP输出文件流
                out.putNextEntry(new ZipEntry(getFileName(fileNames[i])));
                logger.info("文件名:" + fileNames[i]);

                // 把文件输入流拷贝到ZIP文件输出流
                StreamUtils.copy(is, out);

                // 关闭ZIP输出文件定位入口
                out.closeEntry();

                // 关闭输入流
                is.close();
                i++;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            return;
        } finally {
            IOUtils.closeQuietly(out);
        }

    }

    /**
     * 输入流（要求是zip类型的）解压成文件 InputStream(zip) --> 文件
     *
     * @param zipStream
     * @param filePath
     * @return
     */
    public static boolean zipStreamToFiles(InputStream zipStream, String filePath) {

        // 初始化
        ZipInputStream in = null;
        ZipEntry ze = null;

        if (zipStream == null) {
            return false;
        }

        try {

            // 创建ZIP文件输入流
            in = new ZipInputStream(zipStream);

            // 解压ZIP文件
            while ((ze = in.getNextEntry()) != null) {

                // 从文件路径中截取文件名
                String fileName = getFileName(ze.getName());

                // 创建文件输出流
                FileOutputStream outFile = new FileOutputStream(filePath + fileName);

                // 把ZIP文件输出流拷贝到文件输入流
                StreamUtils.copy(in, outFile);

                // 关闭ZIP输入文件定位入口
                in.closeEntry();

                // 关闭文件输出流
                outFile.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            try {
                if (in != null) {
                    // 关闭ZIP输入文件流
                    in.close();
                }
            } catch (IOException e) {
                return false;
            }
        }

        // 返回成功
        return true;
    }

    /**
     * 解压ZIP文件 ZIP文件 --> files
     *
     * @param zipPath  压缩文件的完整路径名
     * @param filePath 文件的完整路径名
     * @return
     */
    public static boolean zipToFiles(String filePath, String zipPath) {
        // 初始化
        boolean isSuccess = false;

        if (!zipFormat(zipPath)) {
            // 目标压缩文件路径非法，失败
            return isSuccess;
        }

        // 创建文件输入流
        try {
            FileInputStream in = new FileInputStream(zipPath);
            isSuccess = zipStreamToFiles(in, filePath);
        } catch (FileNotFoundException e) {
            return isSuccess;
        }

        // 返回成功
        return isSuccess;
    }

    /**
     * 把这些文件压缩成ZIP文件 files --> ZIP文件
     *
     * @param filePaths  这些文件的完整路径名
     * @param targetPath 生成ZIP文件完整路径名
     * @return
     */
    public static boolean filesToZip(String[] filePaths, String zipPath) {
        // 初始化
        ZipOutputStream out = null;

        if (filePaths == null) {
            // 压缩的文件路径为空，失败
            return false;
        }

        if (!zipFormat(zipPath)) {
            // 目标压缩文件路径非法，失败
            return false;
        }

        try {
            // 创建ZIP文件输出流
            out = new ZipOutputStream(new FileOutputStream(zipPath));

            // 压缩文件
            for (int i = 0; i < filePaths.length; i++) {

                // 创建文件输入流
                FileInputStream in = new FileInputStream(filePaths[i]);

                // 添加 ZIP entry 到 ZIP输出文件流
                out.putNextEntry(new ZipEntry(getFileName(filePaths[i])));
                logger.info("文件路径:" + filePaths[i]);

                // 把文件输入流拷贝到ZIP文件输出流
                StreamUtils.copy(in, out);

                // 关闭ZIP输出文件定位入口
                out.closeEntry();

                // 关闭输入流
                in.close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        } finally {
            try {
                if (out != null) {
                    // 关闭ZIP输出文件流
                    out.close();
                }
            } catch (IOException e) {
                return false;
            }
        }
        // 返回成功
        return true;
    }

    /**
     * 截取文件名
     *
     * @param pathName
     * @return
     */
    public static String getFileName(String pathName) {
        // 初始化
        boolean isFile = StringUtils.contains(pathName, SYMBOL_SLASH);
        // 结果返回
        return (isFile) ? StringUtils.substringAfterLast(pathName, SYMBOL_SLASH) : pathName;
    }

    /**
     * 验证zip文件的扩展名
     *
     * @param zipPath
     * @return
     */
    public static boolean zipFormat(String zipPath) {

        // 初始化

        // zip文件路径为空
        if (StringUtils.isBlank(zipPath)) {
            return false;
        }

        String zipName = StringUtils.substringAfterLast(zipPath, SYMBOL_POINT);

        // 扩展名不为zip格式
        if (!zipName.equals(ZIP_FORMAT)) {
            return false;
        }

        // 结果返回
        return true;
    }

}
