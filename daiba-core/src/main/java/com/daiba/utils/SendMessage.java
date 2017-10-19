package com.daiba.utils;

import com.daiba.weixin.WX;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by tangzuopeng on 2016/10/12.
 */
public class SendMessage {

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //微信发送模板消息，请求方式POST
    public static final String WX_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    //订单状态更新模板ID
    public static final String STATUS_TEMPLAT = "cS96P3xWXSOvCjuVtcxqyfe8grKQjmciKErwZuAUy_M";

    //修改密码模板ID
    public static final String CHANGE_PASSWORD_TEMPLAT = "fbPu2NDM9OolfSTxot9b5y825BhIwBa22_Ti6ZJnCWU";

    //带客申请模板ID
    public static final String APPLY_BRINGER_TEMPLAT = "d0fkCc3IbORs7m4ZR63pO_RLUn1BizIcfO9FbQbFH60";

    //新订单通知模板ID
    public static final String NEW_ORDER_TEMPLAT = "8BTXV7Fw4m7SeTRQ1aRm1UhcnYI1FX4xVXTH-FC4j4Q";

    //接单成功通知模板ID
    public static final String ACCEPT_ORDER_TEMPLAT = "pgW-A5O7cHrHc4KM7JPb2xLX1MGxbkCpPkCzGhgxut0";

    //常用跳转地址
    public static final String GIVE_ORDER_URI = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxefafe5d086334c61&redirect_uri=http://www.ainitzp.cn/daiba-webapp/WeiXin/authorize/&response_type=code&scope=snsapi_userinfo&state=210#wechat_redirect&connect_redirect=1";
    public static final String ACCEPT_ORDER_URI = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxefafe5d086334c61&redirect_uri=http://www.ainitzp.cn/daiba-webapp/WeiXin/authorize/&response_type=code&scope=snsapi_userinfo&state=410#wechat_redirect&connect_redirect=1";

    //管理员openID
    public static final String ZHANG = "og67swqWZHowzN83ToNa97h9isAA";
    public static final String FANG = "og67swiqEUF0dWRQjX2N4nrbYP7g";

    /**
     *
     * @param touser      用户openid
     * @param template_id 模板ID
     * @param clickurl    点击URL
     * @param data        通知内容
     * @return
     */
    public static boolean send(String touser, String template_id, String clickurl, JSONObject data) {

        String token = WX.getInstance().getAccess_token();
        String url = WX_SEND_URL.replace("ACCESS_TOKEN", token);
        JSONObject json = new JSONObject();

        try {
            json.put("touser", touser);
            json.put("template_id", template_id);
            json.put("url", clickurl);
            json.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        System.out.println(json.toString());

        String result = HTTPRequest.ajax(url, json.toString(), HTTPRequest.POST);
        try {
            System.out.println(result);
            JSONObject resultJson = new JSONObject(result);
            String errmsg = (String) resultJson.get("errmsg");
            if (!"ok".equals(errmsg)) {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;

    }

    /**
     * 封装微信模板:订单状态更新
     *
     * @param first       标题
     * @param OrderSn     订单编号
     * @param OrderStatus 订单状态
     * @param remark      备注
     * @return
     */
    public static JSONObject statusJsonmsg(String first, String OrderSn, String OrderStatus, String remark) {
        JSONObject json = new JSONObject();
        JSONObject firstParam = new JSONObject();
        JSONObject OrderSnParam = new JSONObject();
        JSONObject jsonOrderStatus = new JSONObject();
        JSONObject jsonRemark = new JSONObject();
        try {
            firstParam.put("value", first);
            firstParam.put("color", "#173177");
            json.put("first", firstParam);
            OrderSnParam.put("value", OrderSn);
            OrderSnParam.put("color", "#173177");
            json.put("OrderSn", OrderSnParam);
            jsonOrderStatus.put("value", OrderStatus);
            jsonOrderStatus.put("color", "#173177");
            json.put("OrderStatus", jsonOrderStatus);
            jsonRemark.put("value", remark);
            jsonRemark.put("color", "#173177");
            json.put("remark", jsonRemark);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 封装微信模板:修改密码通知
     *
     * @param first    标题
     * @param keyword1 修改时间
     * @param keyword2 操作Ip
     * @param remark   备注
     * @return
     */
    public static JSONObject passwordChangeJsonmsg(String first, String keyword1, String keyword2, String remark) {
        JSONObject json = new JSONObject();
        JSONObject firstParam = new JSONObject();
        JSONObject keyword1Param = new JSONObject();
        JSONObject keyword2Param = new JSONObject();
        JSONObject jsonRemark = new JSONObject();
        try {
            firstParam.put("value", first);
            firstParam.put("color", "#173177");
            json.put("first", firstParam);
            keyword1Param.put("value", keyword1);
            keyword1Param.put("color", "#173177");
            json.put("keyword1", keyword1Param);
            keyword2Param.put("value", keyword2);
            keyword2Param.put("color", "#173177");
            json.put("keyword2", keyword2Param);
            jsonRemark.put("value", remark);
            jsonRemark.put("color", "#173177");
            json.put("remark", jsonRemark);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 封装微信模板:认证带客通知
     *
     * @param first    标题
     * @param keyword1 修改时间
     * @param keyword2 操作Ip
     * @param remark   备注
     * @return
     */
    public static JSONObject applyBringerInform(String first, String keyword1, String keyword2, String remark) {
        JSONObject json = new JSONObject();
        JSONObject firstParam = new JSONObject();
        JSONObject keyword1Param = new JSONObject();
        JSONObject keyword2Param = new JSONObject();
        JSONObject jsonRemark = new JSONObject();
        try {
            firstParam.put("value", first);
            firstParam.put("color", "#173177");
            json.put("first", firstParam);
            keyword1Param.put("value", keyword1);
            keyword1Param.put("color", "#173177");
            json.put("keyword1", keyword1Param);
            keyword2Param.put("value", keyword2);
            keyword2Param.put("color", "#173177");
            json.put("keyword2", keyword2Param);
            jsonRemark.put("value", remark);
            jsonRemark.put("color", "#173177");
            json.put("remark", jsonRemark);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 封装微信模板:新订单通知
     *
     * @param first         标题
     * @param tradeDateTime 提交时间
     * @param orderType     订单类型
     * @param customerInfo  订单类型
     * @param orderItemName 自定义
     * @param orderItemData 自定义
     * @param remark        备注
     * @return
     */
    public static JSONObject newOrderInform(String first, String tradeDateTime, String orderType, String customerInfo, String orderItemName, String orderItemData, String remark) {
        JSONObject json = new JSONObject();
        JSONObject firstParam = new JSONObject();
        JSONObject tradeDateTimeParam = new JSONObject();
        JSONObject orderTypeParam = new JSONObject();
        JSONObject customerInfoParam = new JSONObject();
        JSONObject orderItemNameParam = new JSONObject();
        JSONObject orderItemDataParam = new JSONObject();
        JSONObject jsonRemark = new JSONObject();
        try {
            firstParam.put("value", first);
            firstParam.put("color", "#173177");
            json.put("first", firstParam);
            tradeDateTimeParam.put("value", tradeDateTime);
            tradeDateTimeParam.put("color", "#173177");
            json.put("tradeDateTime", tradeDateTimeParam);
            orderTypeParam.put("value", orderType);
            orderTypeParam.put("color", "#173177");
            json.put("orderType", orderTypeParam);
            customerInfoParam.put("value", customerInfo);
            customerInfoParam.put("color", "#173177");
            json.put("customerInfo", customerInfoParam);
            orderItemNameParam.put("value", orderItemName);
            orderItemNameParam.put("color", "#173177");
            json.put("orderItemName", orderItemNameParam);
            orderItemDataParam.put("value", orderItemData);
            orderItemDataParam.put("color", "#173177");
            json.put("orderItemData", orderItemDataParam);
            jsonRemark.put("value", remark);
            jsonRemark.put("color", "#173177");
            json.put("remark", jsonRemark);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     *
     * @param first
     * @param keyword1 订单编号
     * @param keyword2 接单时间
     * @param remark 备注
     * @return
     */
    public static JSONObject acceptOrderInform(String first, String keyword1, String keyword2, String remark) {
        JSONObject json = new JSONObject();
        JSONObject firstParam = new JSONObject();
        JSONObject keyword1Param = new JSONObject();
        JSONObject keyword2Param = new JSONObject();
        JSONObject jsonRemark = new JSONObject();
        try {
            firstParam.put("value", first);
            firstParam.put("color", "#173177");
            json.put("first", firstParam);
            keyword1Param.put("value", keyword1);
            keyword1Param.put("color", "#173177");
            json.put("keyword1", keyword1Param);
            keyword2Param.put("value", keyword2);
            keyword2Param.put("color", "#173177");
            json.put("keyword2", keyword2Param);
            jsonRemark.put("value", remark);
            jsonRemark.put("color", "#173177");
            json.put("remark", jsonRemark);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

}
