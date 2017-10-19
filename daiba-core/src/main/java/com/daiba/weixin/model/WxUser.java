package com.daiba.weixin.model;

import org.json.JSONException;
import org.json.JSONObject;

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
 * @date　 2016-09-11  17:56
 * @description　 $
 */
public class WxUser {

    private static class key{
        public static String openid = "openid";
        public static String nickname = "nickname";
        public static String sex = "sex";
        public static String province = "province";
        public static String city = "city";
        public static String country = "country";
        public static String headImgUrl = "headimgurl";
        public static String privilege = "privilege";
//        public static String unionId = "unionid";
    }

    private String openid = null;
    private String nickname = null;
    private int sex = 0;
    private String province = null;
    private String city = null;
    private String country = null;
    private String headImgUrl = null;
    private String privilege = null;
//    private String unionId = null;

    public WxUser(){}
    public WxUser(String user) throws JSONException {
        JSONObject userJson = null;
        userJson = new JSONObject(user);
        this.openid = userJson.getString(key.openid);
        this.nickname = userJson.getString(key.nickname);
        this.sex = userJson.getInt(key.sex);
        this.province = userJson.getString(key.province);
        this.city = userJson.getString(key.city);
        this.country = userJson.getString(key.country);
        this.headImgUrl = userJson.getString(key.headImgUrl);
        this.privilege = userJson.getString(key.privilege);
//        this.unionId = userJson.getString(key.unionId);
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

//    public String getUnionId() {
//        return unionId;
//    }

//    public void setUnionId(String unionId) {
//        this.unionId = unionId;
//    }
}
