package com.tao.smart.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author tyq
 * @version 1.0, 2017/11/27
 */
public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将pojo转化为json
     */
    public static <T> String toJson(T obj) {
        String json = null;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("convent pojo to json fail ", e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将json转化为pojo
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo = null;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            LOGGER.error("convent json to pojo fail ", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
