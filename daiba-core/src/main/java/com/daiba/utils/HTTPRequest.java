package com.daiba.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 　 　　   へ　　　 　／|
 * 　　    /＼7　　　 ∠＿/
 * 　     /　│　　 ／　／
 * 　    │　Z ＿,＜　／　　   /`ヽ
 * 　    │　　　 　　ヽ　    /　　〉
 * 　     Y　　　　　   `　  /　　/
 * 　    ｲ●　､　●　　⊂⊃〈　　/
 * 　    ()　 へ　　　　|　＼〈
 * 　　    >ｰ ､_　 ィ　 │ ／／      去吧！
 * 　     / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　     ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　    7　　　　　　　|／
 * 　　    ＞―r￣￣`ｰ―＿
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * @author　 penghaitao
 * @date　 2016-09-04  16:21
 * @description　 $
 */
public class HTTPRequest {

    public static final String GET = "GET";
    public static final String POST = "POST";
    private static final int Timeout = 10 * 1000;

    //  对外开放ajax请求接口
    //  Map
    public static String ajax(String url, HashMap<String, String> params, String type) {
        if (type.equals(GET))
            return  get(url, params);
        else if(type.equals(POST))
            return post(url, toParams(params).toString());
        else
            return "{\"result\" + \"error\"}";
    }
    //  JSON
    public static String ajax(String url, JSONObject params, String type) {
        if (type.equals(GET))
            return  get(url, toHashMap(params));
        else if(type.equals(POST))
            return post(url, params.toString());
        else
            return "{\"result\" + \"error\"}";
    }

    //  xmlString
    public static String ajax(String url, String params, String type) {
        if (type.equals(GET))
            return "{\"result\" + \"error\"; \"message\" + \"GET请求暂不支持XML格式\"}";
        else if(type.equals(POST))
            return post(url, params);
        else
            return "{\"result\" + \"error\"}";
    }




    /**
     * @description 向指定URL发送GET方法的请求
     * @param mUrl 发送请求的URL
     * @param mParams 请求参数，请求参数应该是 key-value键值
     * @return URL 所代表远程资源的响应结果
     */
    private static String get(String mUrl, HashMap<String, String> mParams) {
        //  合成请求字符串
        String request = mUrl + '?' + toParams(mParams);
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(request);
            conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestMethod(GET);// 提交模式
            conn.setConnectTimeout(Timeout);//连接超时 单位毫秒
            conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
            conn.setRequestProperty("Content-Type", "text/xml;encoding=utf-8");
            conn.connect();
            if(conn.getResponseCode() == 200){
                // 定义BufferedReader输入流来读取URL的响应
                return readInputStream(conn.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }

    /**
     * @description 向指定URL发送POST方法的请求
     * @param mUrl 发送请求的URL
     * @param mParams 请求参数，请求参数JSON
     * @return URL 所代表远程资源的响应结果
     */
//    private static String post(String mUrl, JSONObject mParams){
//        try {
//            URL url = new URL(mUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod(POST);// 提交模式
//            conn.setConnectTimeout(Timeout);//连接超时 单位毫秒
//            conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            conn.setRequestProperty("connection", "keep-alive");
//            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
//            conn.setRequestProperty("Content-Type", "text/xml;encoding=utf-8");
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.connect();
//            // 表单参数与get形式一样
//            byte[] bytes = mParams.toString().getBytes("UTF-8");
//            conn.getOutputStream().write(bytes);// 输入参数
//            return readInputStream(conn.getInputStream());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "{}";
//    }

    /**
     * @description 向指定URL发送POST方法的请求
     * @param mUrl 发送请求的URL
     * @param mParams 请求参数，请求参数为xml字符串
     * @return URL 所代表远程资源的响应结果
     */
    private static String post(String mUrl, String mParams){
        try {
            URL url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(POST);// 提交模式
            conn.setConnectTimeout(Timeout);//连接超时 单位毫秒
            conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
            conn.setRequestProperty("Content-Type", "text/xml;encoding=utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            // 表单参数与get形式一样
            byte[] bytes = mParams.getBytes("UTF-8");
            conn.getOutputStream().write(bytes);// 输入参数
            return readInputStream(conn.getInputStream());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "{}";
    }

    /**
     * @description 向指定URL发送POST方法的请求
     * @param mUrl 发送请求的URL
     * @param mParams 请求参数，请求参数应该是 key-value键值
     * @return URL 所代表远程资源的响应结果
     */
//    private static String post(String mUrl, HashMap<String, String> mParams){
//        try {
//            URL url = new URL(mUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod(POST);// 提交模式
//            conn.setConnectTimeout(Timeout);//连接超时 单位毫秒
//            conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            conn.setRequestProperty("connection", "keep-alive");
//            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.connect();
//            // 表单参数与get形式一样
//            byte[] bytes = toParams(mParams).toString().getBytes();
//            conn.getOutputStream().write(bytes);// 输入参数
//            return readInputStream(conn.getInputStream());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return "{}";
//    }

    //  参数合成
    private static HashMap<String, String> toHashMap(JSONObject mParams){
        HashMap<String, String> params = new HashMap<String, String>();
        Iterator it = mParams.keys();
        while(it.hasNext()){
            String key = (String) it.next();
            try {
                params.put(key, mParams.getString(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return params;
    }
    private static String toParams(HashMap<String, String> mParams) {
        StringBuffer params = new StringBuffer();
        for (String key : mParams.keySet()) {
            params.append(key).append("=").append(mParams.get(key)).append("&");
        }
        //  删除最后以一个&
        params.deleteCharAt(params.length() - 1);
        return params.toString();
    }

    /**
     * 将InputStreamReader转化成String
     * @param inputStream URL响应结果
     * @return String 转化结果
     */
    private static String readInputStream(InputStream inputStream) throws Exception{
        // 定义BufferedReader输入流来读取URL的响应
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream,"utf8"));
        String line = null, result = "";
        while ((line = in.readLine()) != null)
        {
            result += line + "\n";
        }
        in.close();
        return result;
    }


}
