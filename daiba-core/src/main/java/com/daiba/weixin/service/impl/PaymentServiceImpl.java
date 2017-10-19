package com.daiba.weixin.service.impl;

import com.daiba.firm.model.Firm;
import com.daiba.order.model.Order;
import com.daiba.utils.*;
import com.daiba.weixin.WX;
import com.daiba.weixin.model.PayAttach;
import com.daiba.weixin.service.PaymentService;
import org.jdom.JDOMException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
 * @author　 Tangzuopeng
 * @date　 2016-09-15  16:34
 * @description　 $
 */
@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    public Map postPayMessage(String firmId, String fee, int userId, String openid, HttpServletRequest request) throws IOException, JDOMException {

        Map<String, String> params = new HashMap<String,String>();
        params.put("appid", WX.AppID);
        params.put("mch_id", ConfigUtil.MCH_ID);
        params.put("nonce_str", RandomString.getRandomString(32));
        params.put("body", ConfigUtil.PRODUCTS_DESCRIPTION);
        params.put("device_info", "WEB");
        params.put("out_trade_no", firmId);
        params.put("spbill_create_ip", GetHostIp.getIpAddr(request));
        params.put("notify_url", ConfigUtil.NOTIFY_URL);
        params.put("trade_type", "JSAPI");
        params.put("openid", openid);
        params.put("total_fee", fee);
        String sign = Signature.getSign(params);
        params.put("sign", sign);
//        System.out.println("#PaymentService.params#");
//        System.out.println(params);
        String requestXML = XMLUtil.getRequestXml(params);
//        System.out.println("#PaymentService.#requestXML");
//        System.out.println(requestXML);
        String result = HTTPRequest.ajax(ConfigUtil.UNIFIED_ORDER_URL, requestXML, HTTPRequest.POST);
//        System.out.println("#PaymentService.#result");
//        System.out.println(result);
        Map<String, String> map = XMLUtil.doXMLParse(result);//解析微信返回的信息，以Map形式存储便于取值
//        System.out.println("#PaymentService.#returnMap");
//        System.out.println(map);
        return map;

    }


}
