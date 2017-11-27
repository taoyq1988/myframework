package com.tao.smart.framework.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author tyq
 * @version 1.0, 2017/11/27
 */
public class CastUtils {

    /**
     * 转换为string
     *
     * @param obj
     *
     * @return
     */
    public static String castString(Object obj){
        return castString(obj,"");
    }
    /**
     * 转换为string
     *
     * @param obj
     * @param defaultValue 为空时,或转换失败,返回该值
     *
     * @return
     */
    public static String castString(Object obj, String defaultValue) {
        return obj == null ? defaultValue : String.valueOf(obj);
    }

    public static double castDouble(Object obj){
        return castDouble(obj,0);
    }

    /**
     * 转换为 double
     * @param obj
     * @param defaultValue 为空时,或转换失败,返回该值
     * @return
     */
    public static double castDouble(Object obj,double defaultValue){
        double result = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtils.isNotBlank(strValue)){
                try {
                    result = Double.parseDouble(strValue);
                }catch (NumberFormatException e){
                    result = defaultValue;
                }
            }
        }
        return result;
    }
    public static long castLong(Object obj){
        return castLong(obj,0);
    }
    public static long castLong(Object obj,long defaultValue){
        long result = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtils.isNotBlank(strValue)){
                try {
                    result = Long.parseLong(strValue);
                }catch (NumberFormatException e){
                    result = defaultValue;
                }
            }
        }
        return result;
    }
    public static int castInt(Object obj){
        return castInt(obj,0);
    }
    public static int castInt(Object obj,int defaultValue){
        int result = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtils.isNotBlank(strValue)){
                try {
                    result = Integer.parseInt(strValue);
                }catch (NumberFormatException e){
                    result = defaultValue;
                }
            }
        }
        return result;
    }
    public static boolean castBoolean(Object obj){
        return castBoolean(obj,false);
    }
    public static boolean castBoolean(Object obj,boolean defaultValue){
        boolean result = defaultValue;
        if(obj != null){
            String strValue = castString(obj);
            if(StringUtils.isNotBlank(strValue)) {
                result = Boolean.parseBoolean(strValue);
            }
        }
        return result;
    }

}
