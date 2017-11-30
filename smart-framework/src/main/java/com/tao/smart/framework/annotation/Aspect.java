package com.tao.smart.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 *
 * @author tyq
 * @version 1.0, 2017/11/29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    Class<? extends Annotation> value();
}
