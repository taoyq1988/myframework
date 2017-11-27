package com.tao.smart.framework.helper;

import com.tao.smart.framework.annotation.Inject;
import com.tao.smart.framework.utils.ReflectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手
 *
 * @author tyq
 * @version 1.0, 2017/11/27
 */
public final class IocHelper {

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (MapUtils.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> fieldClass = beanField.getType();
                            Object fieldInstance = beanMap.get(fieldClass);
                            if (fieldInstance != null) {
                                ReflectionUtils.setField(beanInstance, beanField, fieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
