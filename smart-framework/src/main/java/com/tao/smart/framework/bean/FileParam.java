package com.tao.smart.framework.bean;

import java.io.InputStream;

/**
 * 上传文件参数
 *
 * @author tyq
 * @version 1.0, 2017/12/1
 */
public class FileParam {

    /**
     * 文件表单字段名
     */
    private String fieldName;

    private String fileName;

    private Long fileSize;

    /**
     * 上传文件的Content-Type，可判断文件类型
     */
    private String contentType;

    private InputStream inputStream;

    public FileParam(String fieldName, String fileName, Long fileSize, String contentType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
