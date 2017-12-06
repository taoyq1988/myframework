package com.tao.smart.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author tyq
 * @version 1.0, 2017/12/6
 */
public final class ServletHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);

    /**
     * 保证每个线程独有一份ServletHelper实例
     */
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        SERVLET_HELPER_HOLDER.set(new ServletHelper(request, response));
    }

    /**
     * 销毁
     */
    public static void destory() {
        SERVLET_HELPER_HOLDER.remove();
    }

    /**
     * 获取request
     * @return
     */
    private static HttpServletRequest getRequest() {
        return SERVLET_HELPER_HOLDER.get().request;
    }

    /**
     * 获取response
     * @return
     */
    private static HttpServletResponse getResponse() {
        return SERVLET_HELPER_HOLDER.get().response;
    }

    /**
     * 获取session
     * @return
     */
    private static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取ServletContext对象
     * @return
     */
    private static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }


    /** 将属性放入request中 */
    public static void setRequestAttribute(String name,Object value){
        getRequest().setAttribute(name,value);
    }

    /** 从request中获取值 */
    @SuppressWarnings("unchecked")
    public static <T> T getRequestAttribute(String name){
        return (T) getRequest().getAttribute(name);
    }

    /** 从request中移除 **/
    public static void removeRequestAttribute(String name){
        getRequest().removeAttribute(name);
    }

    public static void setSessionAttribute(String key,Object value){
        getSession().setAttribute(key,value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(String name){
        return (T) getSession().getAttribute(name);
    }

    public static void removeSessionAttribute(String name){
        getSession().removeAttribute(name);
    }

    public static void invalidateSession(){
        getSession().invalidate();
    }

    /** 重定向 **/
    public static void sendRedirect(String location){
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            LOGGER.error("sendRedirect fail ",e);
            throw new RuntimeException(e);
        }
    }
}
