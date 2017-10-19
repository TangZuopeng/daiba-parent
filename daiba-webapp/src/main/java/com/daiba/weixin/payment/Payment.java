package com.daiba.weixin.payment;

import com.daiba.BaseController;
import com.daiba.firm.model.Firm;
import com.daiba.firm.service.FirmService;
import com.daiba.user.service.BringerService;
import com.daiba.user.service.UserService;
import com.daiba.utils.*;
import com.daiba.weixin.WX;
import com.daiba.weixin.service.PaymentService;
import org.jdom.JDOMException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
 * @date　 2016-09-15  16:32
 * @description　 $
 */

@Controller
@RequestMapping("/WeiXin/pay")
public class Payment extends BaseController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private FirmService firmService;

    @Autowired
    private UserService userService;

    @Autowired
    private BringerService bringerService;

    /**
     * 传递多个实体类对象
     */
    @InitBinder("firm")
    public void initBinderFirm(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("firm.");
    }

    @InitBinder("order")
    public void initBinderOrder(WebDataBinder binder) {
        binder.setFieldDefaultPrefix("order.");
    }

    //微信支付启动
    @RequestMapping(value = "/pay.do")
    @ResponseBody
    public String pay(HttpServletRequest request){
        String openid  = (String) request.getSession().getAttribute("OPENID");
        String firmId = request.getParameter("firmId");
        String fee = request.getParameter("fee");
//        System.out.println("金额：" + fee);
        int userId = getUserId(request);
//        System.out.println("-----openid-----");
//        System.out.println(openid);
        Map map = null;
        try {
            //在PaymentService中发起预支付请求，得到map
            map = paymentService.postPayMessage(firmId, fee, userId, openid, request);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("appId", WX.AppID);
        params.put("timeStamp", Long.toString(new Date().getTime()));
        params.put("nonceStr", RandomString.getRandomString(32));
        params.put("package", "prepay_id=" + map.get("prepay_id"));
        params.put("signType", ConfigUtil.SIGN_TYPE);
        String paySign =  Signature.getSign(params);
        params.put("paySign", paySign);                                                          //paySign的生成规则和Sign的生成规则一致
        String userAgent = request.getHeader("user-agent");
        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
        params.put("agent", new String(new char[]{agent}));//微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
        JSONObject json = new JSONObject(params);
        System.out.println(json);
        return json.toString();

    }

    @RequestMapping("/success")
    public String paySuccess(HttpSession session){
        if (isLogin(session)) {
            return "wx/subpages/subpagelist";
        } else {
            return "redirect:wx/login/login";
        }
    }

    //微信异步回调通知url，可用以生成订单
    @RequestMapping("/notify")
    synchronized public void payNotify(HttpServletRequest request, HttpServletResponse response, HttpSession session){

        InputStream inputStream = null;

        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;

        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = null;

        try {
            outSteam.close();
            inputStream.close();
            result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Object, Object> map = null;

        try {
            map = XMLUtil.doXMLParse(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }

//        for(Object keyValue : map.keySet()){
//            System.out.println(keyValue+"="+map.get(keyValue));
//        }
        if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
            String firmId = (String) map.get("out_trade_no");
            if(firmService.getFirmStatus(firmId) == 4){
                Integer fee = Integer.valueOf((String)(map.get("cash_fee")));
                try {
                    response.getWriter().write(XMLUtil.setXML("SUCCESS", ""));   //告诉微信服务器，我收到信息了，不要在调用回调action了
                } catch (IOException e) {
                    e.printStackTrace();
                }
                firmService.payFinishFirm(firmId);

                Firm firm = firmService.getFirmDetail(firmId);
                int userId = firm.getUser().getUserId();
                userService.sendOrderSuccess(userId,fee);

                //模板消息所需的订单消息
                String acceptAddress = firm.getOrder().getAcceptAddress();
                String address = firm.getAddress();
                if (acceptAddress == null){
                    acceptAddress = "无";
                }
                if (address == null){
                    address = "无";
                }
                String acceptAddCode = firm.getOrder().getAcceptAddCode();
                String campus = acceptAddCode.substring(0,6);
                List<String> openIds;
                //获取所有带客的openId
                if(campus.equals("100101")){
                    campus = "南湖";
                }else if(campus.equals("100102")){
                    campus = "林园";
                }else if(campus.equals("100103")){
                    campus = "北湖";
                }

                openIds = bringerService.getOpenIdByCampus(campus,"工大",1);

                String orderType;
                int staId = firm.getOrder().getStaId();
                if(staId == 30){
                    orderType = "快递";
                }else if (staId == 31){
                    orderType = "带饭";
                }else {
                    orderType = "其他";
                }

                String name = userService.getSendUserInfo(userId).getName();
                Date time = firm.getGiveTime();
                JSONObject inform = SendMessage.newOrderInform("抢钱啦！", SendMessage.format.format(time), orderType, name, "地点", acceptAddress + "-->" + address, "如需关闭推送，可进入个人中心关闭");
                for(String openId : openIds){
                    //发单提醒所有带客的模板消息
                    SendMessage.send(openId, SendMessage.NEW_ORDER_TEMPLAT, "", inform);
                }
                System.out.println("-------------"+ XMLUtil.setXML("SUCCESS", ""));
            }
        }
    }

    //  调用微信支付企业付款API向带客支付酬劳，给同一openid调用该方法间隔时间不得低于15秒
    /**
     *
     * @param partner_trade_no 商户订单号
     * @param openid 用户openid
     * @param amount 金额
     * @param request
     * @return
     */
    @RequestMapping(value = "confirmReceived.do", method = {RequestMethod.POST})
    @ResponseBody
    public boolean confirmReceived(String partner_trade_no, String openid, String amount, HttpServletRequest request){
        return PayUtil.confirmReceived(partner_trade_no, openid, amount);
    }

    //  取消订单
    //  调用微信支付退款API为取消订单的用户退款
    /**
     *
     * 退款单号与流水号相同
     * 退款金额与金额相同
     * @param out_trade_no 商户订单流水号
     * @param total_fee 订单金额（单位：分）
     * @return
     */
    @RequestMapping(value = "/cancelOrder.do", method = {RequestMethod.POST})
    @ResponseBody
    public boolean cancelOrder(@RequestParam("out_trade_no")String out_trade_no, @RequestParam("total_fee")String total_fee){
        Firm firm = firmService.getFirmDetail(out_trade_no);
        return PayUtil.cancelOrder(out_trade_no, firm);
    }
}
