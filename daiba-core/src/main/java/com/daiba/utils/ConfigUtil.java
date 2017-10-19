package com.daiba.utils;

/**
 * Created by Tangzuopeng on 2016/9/28.
 */

/**
 * 微信支付接口基本信息
 */
public class ConfigUtil {

    //商户号
    public static final String MCH_ID = "1391016702";

    //签名加密方式
    public final static String SIGN_TYPE = "MD5";

    //接收微信支付异步通知回调地址
    public static final String NOTIFY_URL = "http://www.ainitzp.cn/daiba-webapp/WeiXin/pay/notify";

    ////微信支付成功支付后跳转的地址
    public static final String SUCCESS_URL = "http://www.ainitzp.cn/daiba-webapp/WeiXin/pay/success";

    //微信支付统一下单URL
    public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    //微信支付申请退款URl
    public static final String REFUND_ORDER_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    //微信支付企业付款URL
    public static final String TRANSFERS_ORDER_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    //商品描述
    public static final String PRODUCTS_DESCRIPTION = "带吧网络-快递代取";

    //自定义菜单链接
    public static final String MENU_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxefafe5d086334c61&redirect_uri=http://www.ainitzp.cn/daiba-webapp/WeiXin/authorize/&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect&connect_redirect=1";
}
