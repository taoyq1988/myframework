package com.tao.smart.framework.bean;

import com.tao.smart.framework.utils.CastUtils;
import com.tao.smart.framework.utils.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数对象
 *
 * @author tyq
 * @version 1.0, 2017/11/27
 */
public class Param {

    private List<FormParam> formParamList;

    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数映射
     */
    public Map<String, Object> getFieldMap() {
        Map<String, Object> fieldMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(formParamList)) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + StringUtils.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     */
    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> fileMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(fileParamList)) {
            for (FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParamList;
                if (fileMap.containsKey(fieldName)) {
                    fileParamList = fileMap.get(fieldName);
                } else {
                    fileParamList = new ArrayList<>();
                }
                fileParamList.add(fileParam);
                fileMap.put(fieldName, fileParamList);
            }
        }
        return fileMap;
    }

    /**
     * 获取所有上传文件
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     */
    public FileParam getFile(String filedName) {
        List<FileParam> fileParams = getFileList(filedName);
        if (CollectionUtils.isNotEmpty(fileParams) && fileParams.size() == 1) {
            return fileParams.get(0);
        }
        return null;
    }

    /**
     * 获取Long类型
     */
    public Long getLong(String name) {
        return CastUtils.castLong(getFieldMap().get(name));
    }

    /**
     * 获取String
     */
    public String getString(String name) {
        return CastUtils.castString(getFieldMap().get(name));
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(formParamList) && CollectionUtils.isEmpty(fileParamList);
    }
}
