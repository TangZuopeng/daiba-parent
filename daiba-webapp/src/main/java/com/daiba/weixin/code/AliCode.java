package com.daiba.weixin.code;

import com.daiba.BaseController;
import com.daiba.user.model.User;
import com.daiba.user.service.UserService;
import com.daiba.utils.TokenProccessor;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 阿里大于验证码
 * Created by dolphinzhou on 2016/10/3.
 */
@Controller
@RequestMapping(value = "/WeiXin/Code")
public class AliCode extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * 发送注册验证码
     *
     * @param phoneNum 发送验证码到这个手机号码
     * @param session
     * @return 0--重复提交 1--成功  2--发送失败 3--手机号码已经注册 5--未使用带吧微信端登录 6--该微信号已经注册绑定一个手机号码
     */
    @RequestMapping(value = "/sendRegCode", method = RequestMethod.POST)
    @ResponseBody
    public String sendRegCode(@RequestParam("phoneNum") String phoneNum, HttpServletRequest request, HttpSession session) {
        Map<String, Object> regCodeMessages = new HashMap<String, Object>();
        if(isWX(session)){
            boolean flag = TokenProccessor.getInstance().isRepeatSubmit(request);

            String openId = (String) session.getAttribute("OPENID");
            User userInfo = userService.getUserInfoByOpenId(openId);

            if (flag) {
                regCodeMessages.put("codeMessage", "0");
            } else {
                if (userService.hasPhoneNum(phoneNum)) {
                    regCodeMessages.put("codeMessage", "3");
                } else {
                    if(userInfo!=null){
                        regCodeMessages.put("codeMessage", "6");
                    }else{
                        String code = sendCode(phoneNum, regCodeMessages);
                        //方便在在 server 层判断用户名和验证码是否一致
                        session.setAttribute("codePhoneNum", phoneNum);
                        session.setAttribute("code", code);
                        ////验证码发送成功之后去除令牌
                        //session.removeAttribute("token");
                    }
                }
            }
        }else{
            //没有使用带吧微信公众平台
            return "error/authorize_error";
        }
        return new JSONObject(regCodeMessages).toString();
    }

    /**
     * 发送忘记密码验证码
     *
     * @param phoneNum 发送验证码到这个手机号码
     * @param session
     * @return 0--重复提交 1--成功 2--发送失败 4--手机号码未注册 5--未使用带吧微信端登录
     */
    @RequestMapping(value = "/sendForgetCode", method = RequestMethod.POST)
    @ResponseBody
    public String sendForgetCode(@RequestParam("phoneNum") String phoneNum, HttpServletRequest request, HttpSession session) {
        Map<String, Object> forgetCodeMessages = new HashMap<String, Object>();
        if(isWX(session)){
            boolean flag = TokenProccessor.getInstance().isRepeatSubmit(request);
            if(flag){
                forgetCodeMessages.put("codeMessage", "0");
            }else{
                if (userService.hasPhoneNum(phoneNum)) {
                    String code = sendCode(phoneNum, forgetCodeMessages);
                    //方便在在 server 层判断用户名和验证码是否一致
                    session.setAttribute("codePhoneNum", phoneNum);
                    session.setAttribute("code", code);
                    ////验证码发送成功之后去除令牌
                    //session.removeAttribute("token");
                } else {
                    forgetCodeMessages.put("codeMessage", "4");
                }
            }
        }else{
            //没有使用带吧微信公众平台
            return "error/authorize_error";
        }
        return new JSONObject(forgetCodeMessages).toString();
    }

    /**
     * 发送验证码阿里大于实现
     *
     * @param phoneNum
     * @param codeMessages
     * @return 验证码
     */
    private String sendCode(String phoneNum, Map<String, Object> codeMessages) {
        String url = "http://gw.api.taobao.com/router/rest";
        String appkey = "23468404";
        String secret = "3c0ff07009eb4dfe672b61d078fa503b";
        //并发性生成随机数
        String code = ThreadLocalRandom.current().nextInt(100000, 1000000) + "";
        //存放短信模板所需的信息
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        map.put("product", "带吧网络用户");
        String json = new JSONObject(map).toString();
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        //用户可以根据该手机号码识别是哪位会员使用了应用
        req.setExtend(phoneNum);
        req.setSmsType("normal");
        req.setSmsFreeSignName("带吧网络");
        req.setSmsParamString(json);
        req.setRecNum(phoneNum);
        //阿里大于短信模板编号
        req.setSmsTemplateCode("SMS_16726055");
        try {
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            codeMessages.put("codeMessage", "1");
        } catch (Exception e) {
            // TODO: handle exception
            codeMessages.put("codeMessage", "2");
            throw new RuntimeException(e);
        }
        return code;
    }

    ///**
    // * 判断客户端提交上来的令牌和服务器端生成的令牌是否一致
    // *
    // * @param request
    // * @return true 用户重复提交了表单  false 用户没有重复提交表单
    // */
    //private boolean isRepeatSubmit(HttpServletRequest request) {
    //    String browser_token = request.getParameter("token");
    //    //1、如果用户提交的表单数据中没有token，则用户是重复提交了表单
    //    if (browser_token == null) {
    //        return true;
    //    }
    //    //取出存储在Session中的token
    //    String server_token = (String) request.getSession().getAttribute("token");
    //    //2、如果当前用户的Session中不存在Token(令牌)，则用户是重复提交了表单
    //    if (server_token == null) {
    //        return true;
    //    }
    //    //3、存储在Session中的Token(令牌)与表单提交的Token(令牌)不同，则用户是重复提交了表单
    //    if (!browser_token.equals(server_token)) {
    //        return true;
    //    }
    //    return false;
    //}
}
