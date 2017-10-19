package com.daiba.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qiuyong
 * @date 2016/10/10.
 * @declare 对于一些特殊字符串可用来处理
 */
public class StringUtil {
    /**
     * 将存在E盘的用户头像的物理路径变成可以在tomcat下访问的url
     * 例如 E:\\daiba\\user\\13166837708\\b6881504-7b75-4ada-97c5-25bf0a2f0f051.png
     * 转换结果 user/13166837708/b6881504-7b75-4ada-97c5-25bf0a2f0f051.png
     * @param physicPath 存储图片的物理路径
     * @return  url 可以在tomcat下访问的实际路径
     */
    public static String subPhysiclPathToUrl(String physicPath,HttpServletRequest request){
       // String str="\\user\\13166837708\\b6881504-7b75-4ada-97c5-25bf0a2f0f051.png";
        StringBuffer sb=new StringBuffer();
        //String str=physicPath.substring(8);
        String str1=physicPath.replace("\\", "/");
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
        String url=sb.append(basePath).append(str1).toString();
        System.out.println(url);
        return url;
    }
    /**
     * 将存在E盘的用户头像的物理路径变成可以在tomcat下访问的url
     * 例如 E:\\daiba\\user\\13166837708\\b6881504-7b75-4ada-97c5-25bf0a2f0f051.png
     * 转换结果 user/13166837708/b6881504-7b75-4ada-97c5-25bf0a2f0f051.png
     * @param physicPath 存储图片的物理路径
     * @return  url 可以在tomcat下访问的实际路径
     */
    public static String subPhysiclPathToUrl(String physicPath){
       // String str="E:\\daiba\\user\\13166837708\\b6881504-7b75-4ada-97c5-25bf0a2f0f051.png";
        StringBuffer sb=new StringBuffer();
        String str=physicPath.substring(8);
        String str1=str.replace("\\", "/");
        String url=sb.append(str1).toString();
        System.out.println(url);
        return url;
    }



}
