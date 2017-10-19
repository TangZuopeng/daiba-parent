package com.daiba.weixin.payment;

import com.daiba.firm.model.Firm;
import com.daiba.order.model.Order;
import com.daiba.utils.*;
import com.daiba.utils.Signature;
import com.daiba.weixin.WX;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tangzuopeng on 2016/11/6.
 */
public class PayUtil {

    private static String IP = "123.207.149.13";

    /**
     * 企业付款方法
     * @param partner_trade_no 商户支付订单号
     * @param openid 用户openid
     * @param amount 支付金额（不得小于1元）
     * @return
     */
    public static boolean confirmReceived(String partner_trade_no, String openid, String amount){
        Map<String, String> params = new HashMap<>();
        params.put("mch_appid", WX.AppID);
        params.put("mchid", ConfigUtil.MCH_ID);
        params.put("nonce_str", RandomString.getRandomString(32));
        String sign = Signature.getSign(params);
        params.put("sign", sign);
        params.put("partner_trade_no", partner_trade_no);
        params.put("openid", openid);
        params.put("check_name", "NO_CHECK");
        params.put("amount", amount);
        params.put("desc", "带客酬劳");
        params.put("spbill_create_ip", IP);

        //使用XML工具类将map转成XML格式字符串
        String requestXML = XMLUtil.getRequestXml(params);
        String result = useCER(requestXML, ConfigUtil.TRANSFERS_ORDER_URL);

        Map<String, String> map = null;
        try {
            map = XMLUtil.doXMLParse(result);//解析微信返回的信息，以Map形式存储便于取值
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        if (map.get("return_code").equals("FAIL")||map.get("result_code").equals("FAIL")){
            return false;
        }
        return true;
    }

    //  取消订单
    //  调用微信支付退款API为取消订单的用户退款
    /**
     *
     * 退款单号与流水号相同
     * 退款金额与金额相同
     * @param out_trade_no 商户订单流水号
     * @param firm 退款订单详情
     * @return
     */
    public static boolean cancelOrder(String out_trade_no, Firm firm){
        Order order = firm.getOrder();
        Map<String, String> params = new HashMap<>();
        params.put("appid", WX.AppID);
        params.put("mch_id", ConfigUtil.MCH_ID);
        params.put("nonce_str", RandomString.getRandomString(32));
        params.put("out_trade_no", out_trade_no);
        params.put("out_refund_no",out_trade_no);
        int total_fee = 0;
//        if (order.getStaId() == 31){
//            if (firm.getGoodsMoney() > 700){
//                total_fee = firm.getOrderMoney() + firm.getGoodsMoney() - 150;
//            }
//        } else {
//            total_fee = firm.getOrderMoney() + firm.getGoodsMoney();
//        }
        total_fee = firm.getOrderMoney() + firm.getGoodsMoney();
        params.put("total_fee", "" + total_fee);
        params.put("refund_fee", "" + total_fee);
        params.put("op_user_id", ConfigUtil.MCH_ID);
        String sign = Signature.getSign(params);
        params.put("sign", sign);
        System.out.println("#refund.params#");
        System.out.println(params);
        String requestXML = XMLUtil.getRequestXml(params);
        String result = useCER(requestXML, ConfigUtil.REFUND_ORDER_URL);
        System.out.println("#refund.#result");
        System.out.println(result);
        Map<String, String> map = null;
        try {
            map = XMLUtil.doXMLParse(result);//解析微信返回的信息，以Map形式存储便于取值
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        if (map.get("return_code").equals("FAIL")||map.get("result_code").equals("FAIL")){
            return false;
        }
        return true;
    }

    //使用商户证书，安全级别较高
    private static String useCER(String data, String url){

        KeyStore keyStore  = null;
        try {
            keyStore = KeyStore.getInstance("PKCS12");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        FileInputStream instream = null;
        try {
            instream = new FileInputStream(new File("C:/cert/apiclient_cert.p12"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(instream, "1391016702".toCharArray());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        try {
            instream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, "1391016702".toCharArray()).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,new String[] { "TLSv1" },null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom() .setSSLSocketFactory(sslsf) .build();
        HttpPost httpost = new HttpPost(url);
        httpost.addHeader("Connection", "keep-alive");
        httpost.addHeader("Accept", "*/*");
        httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpost.addHeader("Host", "api.mch.weixin.qq.com");
        httpost.addHeader("X-Requested-With", "XMLHttpRequest");
        httpost.addHeader("Cache-Control", "max-age=0");
        httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
        httpost.setEntity(new StringEntity(data, "UTF-8"));
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
