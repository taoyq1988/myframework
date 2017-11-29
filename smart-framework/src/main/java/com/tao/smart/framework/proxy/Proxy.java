package com.tao.smart.framework.proxy;

/**
 * 代理接口
 *
 * @author tyq
 * @version 1.0, 2017/11/29
 */
public interface Proxy {

    /**
     * 执行链式代理
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
