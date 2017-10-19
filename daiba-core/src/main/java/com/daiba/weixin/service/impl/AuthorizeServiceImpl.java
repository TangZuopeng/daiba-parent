package com.daiba.weixin.service.impl;

import com.daiba.utils.ConfigUtil;
import com.daiba.utils.HTTPRequest;
import com.daiba.weixin.WX;
import com.daiba.weixin.model.WxUser;
import com.daiba.weixin.service.AuthorizeService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;

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
 * @date　 2016-09-09  20:41
 * @description　 $
 */
@Service("authorizeService")
public class AuthorizeServiceImpl implements AuthorizeService {

    public JSONObject getToken(String code) throws JSONException {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("appid", WX.AppID);
        params.put("secret", WX.AppSecret);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        String request = HTTPRequest.ajax(url, params, HTTPRequest.GET);
    //    System.out.println("Token_# " + request);
        return new JSONObject(request);
    }

    public JSONObject checkToken(String token, String openId) throws JSONException {
        String url = "https://api.weixin.qq.com/sns/auth";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", token);
        params.put("openid", openId);
        String request = HTTPRequest.ajax(url, params, HTTPRequest.GET);
        return new JSONObject(request);
    }

    public JSONObject checkRefreshToken(String refresh_token) throws JSONException {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("appid", WX.AppID);
        params.put("grant_type", "refresh_token");
        params.put("access_token", refresh_token);
        String request = HTTPRequest.ajax(url, params, HTTPRequest.GET);
        return new JSONObject(request);
    }

    public WxUser getUserInfo(String token, String openId){
        String url = "https://api.weixin.qq.com/sns/userinfo";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("access_token", token);
        params.put("openid", openId);
        params.put("lang", "zh_CN");
        String request = HTTPRequest.ajax(url, params, HTTPRequest.GET);
        System.out.println("UserInfo_# " + request);
        try {
            return new WxUser(request);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
