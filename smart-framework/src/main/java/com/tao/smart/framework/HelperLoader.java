package com.tao.smart.framework;

import com.tao.smart.framework.helper.*;
import com.tao.smart.framework.utils.ClassUtils;

/**
 * 加载Helper
 *
 * @author tyq
 * @version 1.0, 2017/11/27
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> clazz : classList) {
            ClassUtils.loadClass(clazz.getName(), true);
        }
    }
}
