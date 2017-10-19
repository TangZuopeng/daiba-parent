package com.daiba.weixin.base;

import com.daiba.message.model.Article;
import com.daiba.message.model.NewsMessage;
import com.daiba.message.service.MessageService;
import com.daiba.utils.*;
import com.daiba.weixin.WX;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;


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
 * @author　 penghaitao
 * @date　 2016-09-02  22:35
 * @description　 对话服务-接收消息
 */
@Controller
@RequestMapping("/WeiXin/session/receive")
public class ReceiveController {

    @Autowired
    MessageService messageService;
    /*
     *  @description 验证消息真实性
     *  @testUrl
        http://localhost/daiba/WeiXin/session/receive/check?signature=123456&timestamp=234567&nonce=345678&echostr=456789
     */
    @RequestMapping(value = "/check", method = {RequestMethod.GET})
    public void isWeiXin(HttpServletRequest request, HttpServletResponse response) {
        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        //  将token、timestamp、nonce三个参数进行字典序排序
        String[] array = new String[] { WX.TOKEN, timestamp, nonce };

        try {
            Arrays.sort(array);
        }catch (NullPointerException e) {
            System.out.println("array 数组包含空参数");
        //    e.printStackTrace();
            return;
        }

        //  将三个参数字符串拼接成一个字符串进行sha1加密
        String newSign = null;
        PrintWriter out = null;
        try {
            newSign = SHA.encryptSHA(array[0] + array[1] + array[2]);
            out = response.getWriter();
        } catch (IOException e) {
            System.out.println("PrintWriter 输出错误");
        //    e.printStackTrace();
        } catch (Exception e) {
            System.out.println("SHA 加密或其他未知错误");
            e.printStackTrace();
        }

        //   开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if (signature.equals(newSign)) {
            System.out.println("微信服务验证成功！");
            out.println(echostr);
        }else {
            System.out.println("微信服务验证失败！");
            out.println("error");
        }
        out.close();
        out = null;
    }

    @RequestMapping(value = "/check", method = {RequestMethod.POST})
    synchronized public void receiveMessage(HttpServletRequest request, HttpServletResponse response){

        response.setCharacterEncoding("UTF-8");

        InputStream inputStream = null;

        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = null;

        try {
            outSteam.close();
            inputStream.close();
            result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们url的返回信息
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Object, Object> map = null;

        try {
            map = XMLUtil.doXMLParse(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }

        String data = messageService.getMessage(map);
        System.out.println(data);
        try {
            response.getWriter().write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
