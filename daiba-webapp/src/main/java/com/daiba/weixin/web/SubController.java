package com.daiba.weixin.web;

import com.daiba.BaseController;
import com.daiba.dishes.model.FirmDishes;
import com.daiba.dishes.service.DishesService;
import com.daiba.firm.model.Firm;
import com.daiba.firm.service.FirmService;
import com.daiba.order.model.Order;
import com.daiba.order.service.OrderService;
import com.daiba.place.service.PlaceService;
import com.daiba.user.model.User;
import com.daiba.user.service.PersonalService;
import com.daiba.user.service.UserService;
import com.daiba.utils.FirmIdSet;
import com.daiba.utils.JSONUtils;
import com.daiba.utils.SendMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daiba.utils.JSONUtils.objectMapper;

/**
 * @author　 TinyDolphin
 * @date　 2016-09-17  14:01
 * @description　 次级子界面
 */
@Controller
@RequestMapping("/WeiXin/subs")
public class SubController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PersonalService personalService;

    @Autowired
    private FirmService firmService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private UserService userService;

    @Autowired
    private DishesService dishesService;

    //@RequestMapping(value = "/sendOrder", method = {RequestMethod.GET})
    //public String sendOrder(HttpSession session) {
    //    if (isLogin(session)) {
    //        return "wx/subpages/send_order";
    //    } else {
    //        return "redirect:wx/login/login";
    //    }
    //}

    /**
     * 进入订单详情页面
     *
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderInfo", method = {RequestMethod.GET})
    public String orderInfo(HttpSession session, HttpServletRequest request) {
        if (isLogin(session)) {
            return "wx/subpages/order_info";
        } else {
            return "redirect:wx/login/login";
        }
    }

    /**
     * 点击确认接单按钮，获取订单流水线详情，并且firm流水线表单中绑定带客信息
     *
     * @return 带单页面和订单流水线简要信息
     */
    @RequestMapping(value = "/accpetFirms.do", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView acceptFirms(HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        if (isLogin(session)) {
            User user = (User) session.getAttribute("user");
            //获取订单流水线id
            String firmId = request.getParameter("firmId");
            //获取接单用户id
            int bringerId = Integer.parseInt(request.getParameter("bringerId"));
            //获取订单地址码
            String acceptAddCode = request.getParameter("acceptAddCode");

            int status = firmService.getFirmStatus(firmId);
            if (status == 0) {
                //订单详情页面
                modelAndView.setViewName("redirect:/WeiXin/bringer/bringerOrder");
                //订单绑定带客 并 获取订单详情
                Firm firmDetail = firmService.acceptFirm(firmId, bringerId);
                System.out.println(firmDetail);
                //获取发单人简要信息
                int userId = firmDetail.getUser().getUserId();
                User sendUser = userService.getSendUserInfo(userId);
                //获取三级拼接地址
                String orderDetailAdd = placeService.getPlace3NameById(acceptAddCode);

                //提交时间
                Date giveTime = firmDetail.getGiveTime();
                //订单类型
                String orderType = "饭菜";
                //发单人电话
                String senderPhone = sendUser.getPhoneNum();
                //要求时间
                Date askTime = firmDetail.getAskTime();
                //备注
                String remark = firmDetail.getRemark();

                try {
                    modelAndView.addObject("firmDetail", objectMapper.writeValueAsString(firmDetail));
                    modelAndView.addObject("sendUser", objectMapper.writeValueAsString(sendUser));
                    modelAndView.addObject("orderDetailAdd", objectMapper.writeValueAsString(orderDetailAdd));
                    //封装接单模板消息
//                    org.json.JSONObject sendJsonObject = SendMessage.statusJsonmsg("您(" + sendUser.getName() + ")发的单有人接单啦！", firmId, "已接单", "点击进入我发的单查看详情！");
//                    org.json.JSONObject acceptJsonObject = SendMessage.acceptOrderInform("您成功抢到(" + sendUser.getName() + ")发的单！", firmId, SendMessage.format.format(new Date()), "点击进入我接的单查看详情！");
                    //给用户发送接单消息:210
//                    SendMessage.send(sendUser.getOpenId(), SendMessage.STATUS_TEMPLAT, SendMessage.GIVE_ORDER_URI, sendJsonObject);
                    //给带客发送接单消息:410
//                    SendMessage.send(user.getOpenId(), SendMessage.ACCEPT_ORDER_TEMPLAT, SendMessage.ACCEPT_ORDER_URI, acceptJsonObject);
//                    if (firmDetail.getOrder().getStaId() == 33) {
//                        //给商户发送接单消息
//                        sendMessageToMerchant(firmId, giveTime, orderType, senderPhone, askTime, remark);
//                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            } else {
                modelAndView.setViewName("redirect:/WeiXin/main/home");
                modelAndView.addObject("accpetFirmsError", false);
            }
        } else {
            modelAndView.setViewName("redirect:/WeiXin/login");
        }
        return modelAndView;
    }

    private void sendMessageToMerchant(String firmId, Date giveTime, String orderType, String senderPhone, Date askTime, String remark) {
        List<Map<String, Object>> firmMerchantMessages = dishesService.getMerchantMessage(firmId);

        StringBuilder dishesNames = new StringBuilder();
        //StringBuilder dishesPrices = new StringBuilder();
        int dishesPrices = 0;
        dishesNames.append(firmMerchantMessages.get(0).get("name") + "×" + firmMerchantMessages.get(0).get("count") + "  ");
        dishesPrices = dishesPrices + (int) firmMerchantMessages.get(0).get("price") * (int) firmMerchantMessages.get(0).get("discount") / 100 * (int) firmMerchantMessages.get(0).get("count");
        for (int index = 1; index < firmMerchantMessages.size(); index++) {
            Map<String, Object> map = firmMerchantMessages.get(index - 1);
            Map<String, Object> map2 = firmMerchantMessages.get(index);

            String merchantId = (String) map.get("merchant_id");
            String merchantId2 = (String) map2.get("merchant_id");
            if (merchantId.equals(merchantId2)) {
                dishesNames.append(map2.get("name") + "×" + map2.get("count") + "  ");
                dishesPrices = dishesPrices + (int) map2.get("price") * (int) map2.get("discount") / 100 * (int) map2.get("count");
            } else {
                org.json.JSONObject merchantJsonObject = SendMessage.newOrderInform("菜品：" + dishesNames.toString(), SendMessage.format.format(giveTime), orderType, senderPhone, "总金额", String.valueOf((double) dishesPrices * 13 / 2000) + "元", "要求送达时间：" + SendMessage.format.format(askTime) + "\n" + "备注：" + remark);
                SendMessage.send(String.valueOf((map.get("open_id"))), SendMessage.NEW_ORDER_TEMPLAT, "", merchantJsonObject);
                dishesNames.setLength(0);
                dishesPrices = 0;
                dishesNames.append(map2.get("name") + "×" + map2.get("count") + "  ");
                dishesPrices = dishesPrices + (int) map2.get("price") * (int) map2.get("discount") / 100 * (int) map2.get("count");
            }
            if (index == firmMerchantMessages.size() - 1) {
                org.json.JSONObject merchantJsonObject = SendMessage.newOrderInform("菜品：" + dishesNames.toString(), SendMessage.format.format(giveTime), orderType, senderPhone, "总金额", String.valueOf((double) dishesPrices * 13 / 2000) + "元", "要求送达时间：" + SendMessage.format.format(askTime) + "\n" + "备注：" + remark);
                SendMessage.send(String.valueOf((map2.get("open_id"))), SendMessage.NEW_ORDER_TEMPLAT, "", merchantJsonObject);
            }
        }
    }

    /**
     * 获取订单详情(接单人+订单流水线详情+订单详情)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/showFirmDetail.do", method = {RequestMethod.POST})
    @ResponseBody
    public String showFirmDetail(HttpServletRequest request) {
        Map<String, Object> firmDetails = new HashMap<String, Object>();
        //获取订单流水线id
        String firmId = request.getParameter("firmId");
        //获取firm详情
        Firm firmDetail = firmService.getFirmDetail(firmId);
        //购物列表
        List<Map<String, Object>> cartsList = null;
        int bringerUserId = firmDetail.getBriId();
        //获取接单人简要信息
        User bringerUser = userService.getAcceptUserInfo(bringerUserId);
        if (firmDetail.getOrder().getStaId() == 31) {
            cartsList = dishesService.getCarts(firmId);
        }
        if (firmDetail.getUser().getUserId() != getUserId(request)) {
            firmDetail = null;
            bringerUser = null;
            cartsList = null;
        }
        firmDetails.put("firmDetail", firmDetail);
        firmDetails.put("bringerUser", bringerUser);
        firmDetails.put("cartsList", cartsList);
        try {
            System.out.println(objectMapper.writeValueAsString(firmDetails));
            return objectMapper.writeValueAsString(firmDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 进入发单页面
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/enterSubmitOrder", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String enterSubmitOrder(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        int orderType = Integer.parseInt(request.getParameter("orderType"));
        if (isLogin(session)) {
            if (orderType == 30) {
                return "wx/main/submit_express_order";
            } else if (orderType == 31) {
                return "wx/main/dishes";
            } else {
                return "wx/main/submit_other_order";
            }
        } else {
            return "redirect:/WeiXin/login";
        }
    }

    /**
     * 初始化发单页面的部分信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/initSubmit.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String initSubmit(HttpServletRequest request) {
        int userId = getUserId(request);
        try {
            Map<String, Object> resultMap = placeService.showLinkedList(userId);
            return JSONObject.fromObject(resultMap).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

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

    /**
     * 发单订单提交
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/submitOrder.do", method = {RequestMethod.POST}, produces = "text/plain;charset=utf-8")
    @Transactional
    @ResponseBody
    public String submitOrder(HttpServletRequest request, HttpSession session, Order order, Firm firm) {
        //购物车信息
        String cart = request.getParameter("cart");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        int userId = getUserId(request);
        String firmId = FirmIdSet.getFirmId(order.getStaId() + "");
        firm.setFirmId(firmId);
        String orderId = firmId;
        order.setOrderId(orderId);
        try {
            if (order.getStaId() == 33 || order.getStaId() == 31) {
                List<FirmDishes> firmDishesList = JSONUtils.json2list(cart, FirmDishes.class);
                dishesService.addFirmDishes(firmId, firmDishesList);
            }
            orderService.addOrder(order);
            firmService.addFirm(firm, userId, orderId);
            resultMap.put("success", true);
            resultMap.put("firmId", firmId);

            int orderMoney = firm.getOrderMoney();
            int goodMoney = firm.getGoodsMoney();
            //发起付款时金额减免1.5元
//            if ((order.getStaId() == 31 || order.getStaId() == 33) && goodMoney >= 700) {
//                goodMoney -= 150;
//            }
            resultMap.put("fee", orderMoney + goodMoney);

            return JSONObject.fromObject(resultMap).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return errMsg;
        }
    }

    /**
     * 确认收货按钮
     *
     * @param request
     * @return
     */
    /*
    @RequestMapping(value = "/confirmAcceptFirm.do", method = {RequestMethod.POST})
    @ResponseBody
    public String confirmAcceptFirm(HttpServletRequest request) {
        Map<String, Object> confirmMessages = new HashMap<String, Object>();
        User userInfo = (User) request.getSession().getAttribute("user");
        String firmId = request.getParameter("firmId");

        int flag = firmService.confirmFirm(firmId, userInfo.getPhoneNum());
        confirmMessages.put("confirmMessage", flag);

        try {
            return objectMapper.writeValueAsString(confirmMessages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }*/

    /**
     * 用户取消订单
     *
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/cancelFirm.do", method = {RequestMethod.POST})
    @ResponseBody
    public String cancelFirm(HttpSession session, HttpServletRequest request) {
        Map<String, Object> cancelMessages = new HashMap<String, Object>();
        String firmId = request.getParameter("firmId");
        User user = (User) session.getAttribute("user");
        int flag = firmService.cancelFirm(firmId, user.getPhoneNum());
        cancelMessages.put("cancelMessage", flag);
        try {
            return objectMapper.writeValueAsString(cancelMessages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 申请取消订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/applyCancelFirm.do", method = {RequestMethod.POST})
    @ResponseBody
    public String applyCancelFirm(HttpServletRequest request) {
        try {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            String firmId = request.getParameter("out_trade_no");
            User user = getUserInfo(request);
            int resultCode = firmService.applyCancelFirm(firmId, user);
            resultMap.put("cancelMessage", resultCode);
            return objectMapper.writeValueAsString(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
