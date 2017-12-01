package com.tao.smart.framework;

import com.tao.smart.framework.bean.Data;
import com.tao.smart.framework.bean.Handler;
import com.tao.smart.framework.bean.Param;
import com.tao.smart.framework.bean.View;
import com.tao.smart.framework.helper.*;
import com.tao.smart.framework.utils.CodeUtils;
import com.tao.smart.framework.utils.JsonUtils;
import com.tao.smart.framework.utils.ReflectionUtils;
import com.tao.smart.framework.utils.StreamUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器
 *
 * @author tyq
 * @version 1.1, 2017/11/27
 *
 * 1.0 基本请求参数获取
 * 1.1 将请求参数获取剥离到RequestHelper和UploadHelper中 分别对应普通参数和文件参数
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化相关helper
        HelperLoader.init();
        //获取ServletContext对象 用于注册Servlet
        ServletContext servletContext = config.getServletContext();
        //注册处理jsp的Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        //用于处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求方法与路径
        String requestMethod = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        //获取Action处理器
        Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
        if (handler != null) {
            //获取Controller类和其bean实例
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);
            //创建请求参数对象
//            Map<String, Object> paramMap = new HashMap<>();
//            Enumeration<String> paramNames = request.getParameterNames();
//            while (paramNames.hasMoreElements()) {
//                String paramName = paramNames.nextElement();
//                String paramValue = request.getParameter(paramName);
//                paramMap.put(paramName, paramValue);
//            }
//            String body = CodeUtils.encodeURL(StreamUtils.getString(request.getInputStream()));
//            if (StringUtils.isNotEmpty(body)) {
//                String[] params = StringUtils.split(body, "&");
//                if (ArrayUtils.isNotEmpty(params)) {
//                    for (String param : params) {
//                        String[] array = StringUtils.split(param, "=");
//                        if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
//                            String paramName = array[0];
//                            String paramValue = array[1];
//                            paramMap.put(paramName, paramValue);
//                        }
//                    }
//                }
//            }
            Param param;
            if (UploadHelper.isMultipart(request)) {
                param = UploadHelper.createParam(request);
            } else {
                param = RequestHelper.createParam(request);
            }

            //调用action方法
            Method actionMethod = handler.getActionMethod();
            Object result;
            if (param.isEmpty()) {
                result = ReflectionUtils.invokeMethod(controllerBean, actionMethod);
            } else {
                result = ReflectionUtils.invokeMethod(controllerBean, actionMethod, param);
            }

            //处理action方法返回值
            if (result instanceof View) {
                handleViewResult((View) result, request, response);
            } else if (result instanceof Data) {
                handleDataResult((Data) result, request, response);
            }
        }
    }

    private void handleViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //返回jsp对象
        String path = view.getPath();
        if (StringUtils.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //返回json数据
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtils.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
