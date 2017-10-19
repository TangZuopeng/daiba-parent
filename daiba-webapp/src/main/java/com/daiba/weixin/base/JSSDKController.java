package com.daiba.weixin.base;

import com.daiba.utils.ConfigUtil;
import com.daiba.utils.RandomString;
import com.daiba.utils.SHA;
import com.daiba.weixin.WX;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取微信JSSDK配置信息
 * Created by tangzuopeng on 2016/10/28.
 */
@Controller
@RequestMapping("/WeiXin/JSSDK")
public class JSSDKController {

    @RequestMapping("/getJSSDK.do")
    @ResponseBody
    public String getConfig(HttpServletRequest request){

//        System.out.println("JSSDK进入");
        Map<String, String> params = new HashMap<>();
        String url = request.getParameter("mUrl");
//        System.out.println(WX.getInstance().getJsApiTicket());
        String jsapiTicket = WX.getInstance().getJsApiTicket();
        String timestamp = Long.toString(new Date().getTime());
        String nonceStr = RandomString.getRandomString(32);
        params.put("appId", WX.AppID);
        params.put("timestamp", timestamp);
        params.put("nonceStr", nonceStr);
        StringBuffer string1 = new StringBuffer();
        string1.append("jsapi_ticket=")
                .append(jsapiTicket)
                .append("&noncestr=")
                .append(nonceStr)
                .append("&timestamp=")
                .append(timestamp)
                .append("&url=")
                .append(url);
        String sign = null;
        try {
            sign = SHA.encryptSHA(string1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("signature", sign);
//        System.out.println("JSSDK退出");
        return JSONObject.fromObject(params).toString();
    }

}
