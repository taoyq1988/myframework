package com.tao.smart.framework.helper;

import com.tao.smart.framework.bean.FormParam;
import com.tao.smart.framework.bean.Param;
import com.tao.smart.framework.utils.CodeUtils;
import com.tao.smart.framework.utils.StreamUtils;
import com.tao.smart.framework.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 请求助手
 *
 * @author tyq
 * @version 1.0, 2017/12/1
 */
public class RequestHelper {

    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        formParamList.addAll(parseParameterNames(request));
        formParamList.addAll(parseInputStream(request));
        return new Param(formParamList);
    }

    /**
     * 解析参数
     * @param request
     * @return
     */
    private static List<FormParam> parseParameterNames(HttpServletRequest request) {
        /**
         * 这样获取的方式是页面使用的 enctype="application/x-www-form-urlencoded" 以url encoded 提交的。
         * 或则是在url后面添加的get参数什么的正常方式提交的
         */
        List<FormParam> formParamList = new ArrayList<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            // 如果页面传递的是相同name的参数。这里取到的就是一个数组
            String[] parameterValues = request.getParameterValues(paramName);
            if(ArrayUtils.isNotEmpty(parameterValues)){
                Object paramValue;
                if(parameterValues.length == 1){
                    paramValue = parameterValues[0];
                }else{
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < parameterValues.length; i++) {
                        sb.append(parameterValues[i]);
                        if( i != parameterValues.length -1){
                            //用逗号隔开
                            sb.append(StringUtils.SEPARATOR);
                        }
                    }
                    paramValue = sb.toString();
                }
                formParamList.add(new FormParam(paramName,paramValue));
            }
        }
        return formParamList;
    }

    /**
     * 从流中解析参数
     * @return
     */
    private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {
        List<FormParam> formParamList = new ArrayList<>();
        String body = CodeUtils.decodeURL(StreamUtils.getString(request.getInputStream()));
        if(org.apache.commons.lang3.StringUtils.isNotBlank(body)){
            String[] params = org.apache.commons.lang3.StringUtils.split(body, "&");
            for (String param : params) {
                String[] array = org.apache.commons.lang3.StringUtils.split(param, "=");
                if(ArrayUtils.isNotEmpty(array) && array.length == 2){
                    String paramName = array[0];
                    String paramValue = array[1];
                    formParamList.add(new FormParam(paramName,paramValue));
                }
            }
        }
        return formParamList;
    }
}
