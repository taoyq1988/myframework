package com.tao.smart.framework.helper;

import com.tao.smart.framework.utils.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * bean助手类
 *
 * @author tyq
 * @version 1.0, 2017/11/27
 */
public final class BeanHelper {

    /**
     * 存放bean类与实例的映射关系
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static {
        Set<Class<?>> beanSet = ClassHelper.getBeanClassSet();
        for (Class<?> clazz : beanSet) {
            Object object = ReflectionUtils.newInstance(clazz);
            BEAN_MAP.put(clazz, object);
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static <T> T getBean(Class<?> clazz) {
        if (!BEAN_MAP.containsKey(clazz)) {
            throw new RuntimeException("can not get bean by class:" + clazz);
        }
        return (T) BEAN_MAP.get(clazz);
    }

    /**
     * 添加bean
     * @param clazz
     * @param obj
     */
    public static void setBean(Class<?> clazz, Object obj) {
        BEAN_MAP.put(clazz, obj);
    }
}
