package com.tao.smart.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理
 *
 * @author tyq
 * @version 1.0, 2017/11/29
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> clazz = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] parames = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(clazz, method, parames)) {
                before(clazz, method, parames);
                result = proxyChain.doProxyChain();
                after(clazz, method, parames);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy fail ", e);
            error(clazz, method, parames);
            throw e;
        } finally {
            end();
        }
        return result;
    }

    public void begin() {

    }

    public boolean intercept(Class<?> clazz, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> clazz, Method method, Object[] params) throws Throwable {

    }

    public void after(Class<?> clazz, Method method, Object[] params) throws Throwable {

    }

    public void error(Class<?> clazz, Method method, Object[] params) throws Throwable {

    }

    public void end() {

    }
}
