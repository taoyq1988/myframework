package com.tao.smart.framework.bean;

import com.tao.smart.framework.utils.CastUtils;

import java.util.Map;

/**
 * 请求参数对象
 *
 * @author tyq
 * @version 1.0, 2017/11/27
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 获取Long类型
     */
    public Long getLong(String name) {
        return CastUtils.castLong(paramMap.get(name));
    }

    /**
     * 获取所有字段信息
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
