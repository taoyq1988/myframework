package com.tao.smart.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置工具
 *
 * @author tyq
 * @version 1.0, 2017/11/24
 */
public class PropsUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtils.class);

    /**
     * 获取配置文件
     * @param filename
     * @return
     */
    public static Properties loadProps(String filename) {
        Properties properties = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            if (is == null) {
               throw new FileNotFoundException(filename + "file not found");
            }
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            LOGGER.error("load properties fail ", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream fail ", e);
                }
            }
        }
        LOGGER.info("load properties success");
        return properties;
    }

    /**
     * 获取属性
     * @param properties
     * @param key
     * @return
     */
    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }

    /**
     * 获取属性 若配置文件中不存在 则附上默认值
     * @param properties
     * @param key
     * @param defalut
     * @return
     */
    public static String getString(Properties properties, String key, String defalut) {
        return properties.getProperty(key, defalut);
    }

    public static int getInt(Properties properties, String key) {
        return getInt(properties, key, 0);
    }

    public static int getInt(Properties properties, String key, int defalut) {
        int value = defalut;
        if (properties.contains(key)) {
            value = Integer.valueOf(properties.getProperty(key));
        }
        return value;
    }

    public static Long getLong(Properties properties, String key) {
        return getLong(properties, key, 0L);
    }

    public static Long getLong(Properties properties, String key, Long defalut) {
        Long value = defalut;
        if (properties.contains(key)) {
            value = Long.valueOf(properties.getProperty(key));
        }
        return value;
    }

    public static double getDouble(Properties properties, String key) {
        return getDouble(properties, key, 0);
    }

    public static double getDouble(Properties properties, String key, double defalut) {
        double value = defalut;
        if (properties.contains(key)) {
            value = Double.valueOf(properties.getProperty(key));
        }
        return value;
    }

    public static boolean getBoolean(Properties properties, String key) {
        return getBoolean(properties, key, false);
    }

    public static boolean getBoolean(Properties properties, String key, boolean defalut) {
        boolean value = defalut;
        if (properties.contains(key)) {
            value = Boolean.valueOf(properties.getProperty(key));
        }
        return value;
    }
}
