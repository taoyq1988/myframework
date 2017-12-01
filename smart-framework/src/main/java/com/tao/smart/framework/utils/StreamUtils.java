package com.tao.smart.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 流操作工具
 *
 * @author tyq
 * @version 1.0, 2017/11/27
 */
public class StreamUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtils.class);

    /**
     * 从输入流中获取字符串
     */
    public static String getString(InputStream is) {
        StringBuffer sb =  new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            LOGGER.error("get string fail ", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 将输入流拷贝到输出流
     *
     * @param inputStream
     * @param outputStream
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        int length;
        try {
            byte[] buffer = new byte[4 * 1024];
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (IOException e) {
            LOGGER.error("copy stream fail ", e);
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                LOGGER.error("close stream fail ", e);
            }
        }
    }
}
