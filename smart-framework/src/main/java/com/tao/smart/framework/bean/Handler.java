package com.tao.smart.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装Action信息
 *
 * @author tyq
 * @version 1.0, 2017/11/27
 */
public class Handler {

    private Class<?> controllerClass;

    private Method actionMethod;

    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
