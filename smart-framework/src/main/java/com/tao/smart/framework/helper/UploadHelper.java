package com.tao.smart.framework.helper;

import com.tao.smart.framework.bean.FileParam;
import com.tao.smart.framework.bean.FormParam;
import com.tao.smart.framework.bean.Param;
import com.tao.smart.framework.utils.FileUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件上传
 *
 * @author tyq
 * @version 1.0, 2017/12/1
 */
public class UploadHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);

    /**
     * apache commons fileupload 提供的servlet文件上传对象
     */
    private static ServletFileUpload servletFileUpload;

    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(
                new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否为multipart类型
     * @param request
     * @return
     */
    public static boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 创建请求参数
     * @param request
     * @return
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        //表单参数列表
        List<FormParam> formParamList = new ArrayList<>();
        //文件参数列表
        List<FileParam> fileParamList = new ArrayList<>();
        try {
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if(MapUtils.isNotEmpty(fileItemListMap)) {
                for(Map.Entry<String, List<FileItem>> ent : fileItemListMap.entrySet()){
                    String filedName = ent.getKey();
                    List<FileItem> value = ent.getValue();
                    if(CollectionUtils.isNotEmpty(value)){
                        for (FileItem fileItem : value) {
                            //如果是表单属性
                            if(fileItem.isFormField()){
                                String fieldValue = fileItem.getString("utf-8");
                                formParamList.add(new FormParam(filedName, fieldValue));
                            }
                            //如果是文件字段
                            else {
                                //获取真实文件名称，自动去掉路径，防止中文乱码
                                String fileName = FileUtils.getRealFileName(new String(fileItem.getName().getBytes(),"UTF-8"));
                                if(StringUtils.isNotBlank(fileName)){
                                    //字段名称
                                    String fieldName = fileItem.getFieldName();
                                    //文件大小
                                    long size = fileItem.getSize();
                                    //文件类型
                                    String contentType = fileItem.getContentType();
                                    //文件流
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName, fileName, size, contentType, inputStream));
                                }
                            }
                        }
                    }
                }
            }
        } catch (FileUploadException e) {
            LOGGER.error("create Param failure",e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList, fileParamList);
    }

    /**
     * 上传文件
     * @param basePath 基础绝对路径
     * @param fileParam
     */
    public static void uploadFile(String basePath,FileParam fileParam){
        try {
            if(fileParam != null){
                String filePath = basePath + fileParam.getFileName();
                FileUtils.createFile(filePath);
                //使用 apache io工具进行图片的上传
                BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(filePath));
                IOUtils.copy(new BufferedInputStream(fileParam.getInputStream()), output);
                output.flush();
                output.close();
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量上传文件
     * @param basePath
     * @param fileParamList
     */
    public static void uploadFile(String basePath,List<FileParam> fileParamList){
        try {
            if(CollectionUtils.isNotEmpty(fileParamList)){
                for (FileParam fileParam : fileParamList) {
                    if(fileParam != null){
                        String filePath = basePath + fileParam.getFileName();
                        FileUtils.createFile(filePath);
                        //使用 apache io工具进行图片的上传
                        IOUtils.copy(new BufferedInputStream(fileParam.getInputStream()),new BufferedOutputStream(new FileOutputStream(filePath)));
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure",e);
            throw new RuntimeException(e);
        }
    }
}
