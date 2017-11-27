package com.tao.smart.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码与解码操作工具
 *
 * @author tyq
 * @version 1.0, 2017/11/27
 */
public class CodeUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeUtils.class);

    /**
     * 将url编码
     */
    public static String encodeURL(String source) {
        String target = null;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("encode url fail ", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将url解码
     */
    public static String decodeURL(String source) {
        String target = null;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("decode url fail ", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
