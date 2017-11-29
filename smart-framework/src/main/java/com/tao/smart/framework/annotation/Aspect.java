package com.tao.smart.framework.annotation;

import java.lang.annotation.Annotation;

/**
 * 切面注解
 *
 * @author tyq
 * @version 1.0, 2017/11/29
 */
public @interface Aspect {

    Class<? extends Annotation> value();
}
