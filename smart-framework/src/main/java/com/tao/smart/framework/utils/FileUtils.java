package com.tao.smart.framework.utils;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author tyq
 * @version 1.0, 2017/12/1
 */
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 获取真实文件名 去掉文件路劲
     * @param fileName
     * @return
     */
    public static String getRealFileName(String fileName) {
        return FilenameUtils.getName(fileName);
    }

    /**
     * 创建文件
     * @param filePath
     * @return
     */
    public static File createFile(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                org.apache.commons.io.FileUtils.forceMkdir(parentDir);
            }
        } catch (IOException e) {
            LOGGER.error("create file fail ", e);
            throw new RuntimeException(e);
        }
        return file;
    }
}
