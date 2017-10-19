package com.daiba.weixin;

import com.daiba.utils.ConfigUtil;
import com.daiba.utils.GetMaterialList;
import com.daiba.utils.HTTPRequest;
import com.daiba.utils.MessageUtil;
import com.daiba.weixinMenu.model.ClickButton;
import com.daiba.weixinMenu.model.NewsButton;
import com.daiba.weixinMenu.model.ViewButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * @date　 2016-09-04  17:40
 * @description　 WX唯一实例
 */
public class WX {

    private WX(){ }
    private static WX instance = null;

    public static final String TOKEN = "WeiXinToken_daiba";

    public static final String serverUrl = "https://api.weixin.qq.com/cgi-bin/";
    public static final String GrantType = "client_credential";

    //测试号：wxceb6dfad8560c277
    //服务号：wxefafe5d086334c61
    public static final String AppID = "wxefafe5d086334c61";

    //测试号：32fdb38b8e7d75d05b1b98e2eaf5a9f7
    //服务号：fcad129265d90bdb3a6718d536ec000d
    public static final String AppSecret = "fcad129265d90bdb3a6718d536ec000d";

    public static WX getInstance() {
        if (instance == null)
            instance = new WX();
        return instance;
    }

    public void initWX(){
        setAccess_token();
        setJsApiTicket();
    }

    private String access_token = null;

    public String getAccess_token() {
        while (access_token == null){
            System.out.println("waiting...");
        }
        return access_token;
    }

    private void setAccess_token() {
        new Thread(new Runnable() {
            public void run() {
                String url = serverUrl + "token";
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("grant_type", WX.GrantType);
                params.put("appid", WX.AppID);
                params.put("secret", WX.AppSecret);
                updateAccess_token(url, params);
            }
        }).start();
    }
    private String ticket = null;
    public String getJsApiTicket(){
        return ticket;
    }
    private void setJsApiTicket(){
        new Thread(new Runnable() {
            public void run() {
                HashMap<String, String> params = new HashMap<>();
                params.put("access_token", getAccess_token());
                params.put("type", "jsapi");
                String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
                updateJsApi(url, params);
            }
        }).start();
    }

    private void updateJsApi(final String url, final HashMap<String, String> params){
        try {
            JSONObject jsonObject = new JSONObject(HTTPRequest.ajax(url, params, HTTPRequest.GET));
            ticket = jsonObject.getString("ticket");
            int timelimit = jsonObject.getInt("expires_in");
            System.out.println("------------------ticket--------------------");
            System.out.println(ticket);
            Thread.sleep((timelimit-60) * 1000);
            updateJsApi(url, params);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //  获取access_token
    private void updateAccess_token(final String url, final HashMap<String, String> params) {
        try {
            JSONObject jsonObject = new JSONObject(HTTPRequest.ajax(url, params, HTTPRequest.GET));
            access_token = jsonObject.getString("access_token");
            int timelimit = jsonObject.getInt("expires_in");
            System.out.println("------------------access_token-------------------");
            System.out.println(access_token);
            Thread.sleep((timelimit-60) * 1000);
            updateAccess_token(url, params);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean initMenu(){
        JSONObject jsonObject = customMenu();
        System.out.print(jsonObject.toString());
        try {
            if (jsonObject != null){
                if (jsonObject.getInt("errcode") == 0) {
                    return true;
                }else {
                    System.out.println(jsonObject.getString("errmsg"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    //自定义菜单
    private JSONObject customMenu(){

        //带吧校园菜单栏
        ViewButton schoolCard = new ViewButton();
        schoolCard.setUrl(MessageUtil.SCHOOL_CARD_URL);
        schoolCard.setName("校园卡余额");
        schoolCard.setType("view");

        ViewButton fourSet = new ViewButton();
        fourSet.setUrl(MessageUtil.SET_URL);
        fourSet.setName("四六级查询");
        fourSet.setType("view");

        net.sf.json.JSONArray subButton1=new net.sf.json.JSONArray();
        subButton1.add(schoolCard);
        subButton1.add(fourSet);

        net.sf.json.JSONObject daibaSchool=new net.sf.json.JSONObject();
        daibaSchool.put("name", "带吧校园");
        daibaSchool.put("sub_button", subButton1);

        //带吧生活菜单栏
        ViewButton home = new ViewButton();
        home.setUrl(ConfigUtil.MENU_URL.replace("STATE", "110"));
        home.setName("带吧生活");
        home.setType("view");

        //更多菜单栏
        ViewButton single = new ViewButton();
        single.setUrl(MessageUtil.SINGLE_URL);
        single.setName("单身展览馆");
        single.setType("view");

        ViewButton biaobai = new ViewButton();
        biaobai.setUrl(MessageUtil.BIAOBAI_URL);
        biaobai.setName("工大表白墙");
        biaobai.setType("view");

        ViewButton lost = new ViewButton();
        lost.setUrl(MessageUtil.LOST_URL);
        lost.setName("失物招领");
        lost.setType("view");

        ViewButton secret = new ViewButton();
        secret.setUrl(MessageUtil.SECRET_URL);
        secret.setName("工大小秘密");
        secret.setType("view");

        ClickButton guide = new ClickButton();
        guide.setKey("guide");
        guide.setName("操作指南");
        guide.setType("click");

        net.sf.json.JSONArray subButton3=new net.sf.json.JSONArray();
        subButton3.add(single);
        subButton3.add(biaobai);
        subButton3.add(secret);
        subButton3.add(lost);
        subButton3.add(guide);

        net.sf.json.JSONObject daibaMore=new net.sf.json.JSONObject();
        daibaMore.put("name", "更多");
        daibaMore.put("sub_button", subButton3);

        net.sf.json.JSONArray button=new net.sf.json.JSONArray();
        button.add(home);
        button.add(daibaSchool);
        button.add(daibaMore);

        net.sf.json.JSONObject menujson=new net.sf.json.JSONObject();
        menujson.put("button", button);
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + getAccess_token();

        try{
            String result = HTTPRequest.ajax(url, menujson.toString(), HTTPRequest.POST);
            System.out.println(result);
            return new JSONObject(result);
        }catch(Exception e){
            System.out.println("请求错误！");
        }
        return null;
    }
}