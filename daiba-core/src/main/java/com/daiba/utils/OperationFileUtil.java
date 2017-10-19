package com.daiba.utils;

import org.apache.commons.io.FilenameUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 操作文件工具类
 * Created by dolphinzhou on 2016/9/29.
 */
public final class OperationFileUtil {

    /**
     * 根据图片URL地址，上传网络图片到服务器，并存入指定的路径下，文件名为用户上传文件名
     *
     * @param iamgeUrl 网络图片的地址
     * @param filePath 保存文件的路径
     * @return
     */
    public static String uploadImage(String iamgeUrl, String filePath) {
        String fileName = FilenameUtils.getName(iamgeUrl);
        return uploadImageAssist(iamgeUrl, filePath, fileName);
    }

    /**
     * 根据图片URL地址，上传网络图片到服务器，并存入指定的路径下，文件名自定义
     *
     * @param iamgeUrl 网络图片的地址
     * @param filePath 保存文件的路径
     * @param fileName 自定义的文件名
     * @return
     */
    public static String uploadImage(String iamgeUrl, String filePath, String fileName) {
        return uploadImageAssist(iamgeUrl, filePath, fileName);
    }

    /**
     * 文件上传辅助
     *
     * @param iamgeUrl 网络图片的地址
     * @param filePath 保存文件的路径
     * @param fileName 保存文件的文件名
     * @return 返回 文件 路径+文件名（无后缀）
     */
    private static String uploadImageAssist(String iamgeUrl, String filePath, String fileName) {
        //文件后缀名
        //String filesuffix = iamgeUrl.substring(iamgeUrl.lastIndexOf('.'));
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        File file = new File(filePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        URL url = null;
        int BUFFER_SIZE = 1024;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        try {
            url = new URL(iamgeUrl);
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            //fos = new FileOutputStream(filePath + fileName + filesuffix);
            fos = new FileOutputStream(filePath + fileName);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            fos.flush();
        } catch (IOException e) {
        } catch (ClassCastException e) {
        } finally {
            try {
                fos.close();
                bis.close();
                httpUrl.disconnect();
            } catch (IOException e) {
            } catch (NullPointerException e) {
            }
        }
        //return filePath + fileName + filesuffix;
        return filePath + fileName;
    }
}