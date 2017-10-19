package com.daiba.weixin.base;

import com.daiba.weixin.model.WxUser;
import com.daiba.weixin.service.AuthorizeService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

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
 * @date　 2016-09-08  23:08
 * @description　 微信身份用户信息获取
 */
@Controller
@RequestMapping("/WeiXin/authorize")
public class Authorize {
    @Autowired
    private AuthorizeService authorizeService;

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpServletRequest request, HttpServletResponse response) {

        JSONObject tokenRequest = null;
        WxUser wxUserRequest = null;

        //  跳转网页类型
        String stateUrl = request.getParameter("state");

        wxUserRequest = getBySession(request);
/*        if (wxUserRequest == null) {    //  若session获取失败则通过cookie刷新token
            tokenRequest = getByCookie(request);
            if (tokenRequest != null) {
                try {
                    wxUserRequest = saveAndGet(tokenRequest, request, response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }*/
        if (wxUserRequest == null){   //  通过code获取
            System.out.println("#Code#");
            String code = request.getParameter("code");
            try {
                tokenRequest = authorizeService.getToken(code);
                System.out.println("#tokenRequest:" + tokenRequest.toString());
                wxUserRequest = saveAndGet(tokenRequest,request, response);

                //为了注册方便获取微信用户信息
                // ****************************** 此处会报错，当这种形式下，未获取到wxUserRequest(微信信息)的时候，报空指针异常
                request.getSession().setAttribute("NICKNAME", wxUserRequest.getNickname());
                request.getSession().setAttribute("HEADIMGURL", wxUserRequest.getHeadImgUrl());
                request.getSession().setAttribute("SEX", wxUserRequest.getSex());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (wxUserRequest == null){   //以上途径获取均失败
            System.out.println("用户信息授权失败");
            return "redirect:/error/authorize";
        }
        //将跳转网页类型放入session域
        request.getSession().setAttribute("state", stateUrl);
        //  url重定向
        if (stateUrl.equals("110")){
            return "redirect:/WeiXin/main/home";
        }else if (stateUrl.equals("210")){
            return "redirect:/WeiXin/main/order";
        }else if(stateUrl.equals("310")){
            return "redirect:/WeiXin/main/mine";
        }else if(stateUrl.equals("410")){
            return "redirect:/WeiXin/bringer/bringerOrder";
        }
        return "redirect:/WeiXin/main/home";
    }

    //  通过session中的token，openId获取用户数据
    private WxUser getBySession(HttpServletRequest request){
        String token = (String)request.getSession().getAttribute("WEB_TOKEN");
        String openId = (String)request.getSession().getAttribute("OPENID");
        //  判断token，openId是否存在
        if (token == null || openId == null)
            return null;
        //  判断token，openId是否过期
        try {
            JSONObject result = authorizeService.checkToken(token, openId);
            if (result.getInt("errcode") == 0) {
                System.out.println("#Session#");
                return authorizeService.getUserInfo(token, openId);
            }
            else
                System.out.println(result.getString("errmsg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //  通过cookie中的refresh_token刷新获取新的token，openId
    private JSONObject getByCookie(HttpServletRequest request) {
        //  将cookie转换到Map里面
        Map<String, String> cookieMap = new HashMap<String,String>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        }
        String openId = (String)request.getSession().getAttribute("OPENID");
        if (openId == null)
            return null;
        //  从cookie中获取到refresh_token
        String refresh_token = cookieMap.get(openId);
        //  判断trefresh_token是否存在
        if (refresh_token == null)
            return null;
        //  判断refresh_token是否过期
        try {
            JSONObject result = authorizeService.checkRefreshToken(refresh_token);
            if (result.has("access_token")){
                System.out.println("#Cookie#");
                return result;
            }
            else
                System.out.println(result.getString("errmsg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //#tokenRequest:{"scope":"snsapi_userinfo","openid":"og67swvUN6VxmGxrv3b4sm1gz-o0","expires_in":7200,"
    //    refresh_token":"C4dhKeIkZ90fHjJgFwRxcKdWY-Qg_CoJr6wMQM7WLfE-LxzluNwaNqXUH99K-joklvKe5cGu6acG6MP-AFoX
    //    TJNqKDlVyUlfFDkZCObr0Zo","access_token":"L7emXF83LjNvwqqOvrNrXPbVRFIjJeT7N8TPt1ExPt0eABhcDeVn6hBny8k
    //    9Rb2fpHVjK2_KtR_kCzE650uK6a4zk3bGkftChv28vuxrMQc"}
    //    UserInfo_# {"openid":"og67swvUN6VxmGxrv3b4sm1gz-o0","nickname":"Mr.zhou","sex":1,"LANGUAGE":"zh_CN",
    //            "city":"长春","province":"吉林","country":"中国","headimgurl":"http:\/\/wx.qlogo.cn\/mmopen\/jT6objP
    //        Kd8hiamOGTBbKWEYhW2hdNIZ9tEa7okjyA4kBPleIQvfUp7iaI4u0FGSlpxrsFfs04HESrghZgDEiauAZOVh3sqyVUwj\/0","pr
    //        ivilege":[]}
    //  保存记录
    private WxUser saveAndGet(JSONObject jsonObject, HttpServletRequest request, HttpServletResponse response) throws JSONException{
        String token = jsonObject.getString("access_token");
        String openId = jsonObject.getString("openid");
        String refresh = jsonObject.getString("refresh_token");
        //  token,openId写入session
        request.getSession().setAttribute("WEB_TOKEN", token);
        request.getSession().setAttribute("OPENID", openId);
        //request.getSession().setAttribute("NICKNAME", nickname);
        //request.getSession().setAttribute("HEADIMGURL", headimgurl);
        //request.getSession().setAttribute("SEX", sex);


/*        //  以openID为key保存refresh值
        Cookie cookie = new Cookie(openId, refresh);
        //  cookie期限为一个月
        cookie.setMaxAge(30 * 24 * 60 * 60);
        //  添加Cookie
        response.addCookie(cookie);

        //  从cookie中获取到refresh_token
        System.out.println("save_cookie);
        Map<String, String> cookieMap = new HashMap<String,String>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookieTemp : cookies){
                cookieMap.put(cookieTemp.getName(), cookieTemp.getValue());
            }
        }
        System.out.println(cookieMap.get(openId));*/
        //  获取用户信息
        return authorizeService.getUserInfo(token, openId);
    }

}
