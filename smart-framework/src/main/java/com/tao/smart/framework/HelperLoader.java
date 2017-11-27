package com.tao.smart.framework;

import com.tao.smart.framework.helper.BeanHelper;
import com.tao.smart.framework.helper.ClassHelper;
import com.tao.smart.framework.helper.ControllerHelper;
import com.tao.smart.framework.helper.IocHelper;
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
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> clazz : classList) {
            ClassUtils.loadClass(clazz.getName(), true);
        }
    }
}
