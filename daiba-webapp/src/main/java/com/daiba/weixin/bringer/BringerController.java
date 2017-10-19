package com.daiba.weixin.bringer;

import com.daiba.BaseController;
import com.daiba.dishes.service.DishesService;
import com.daiba.firm.model.Firm;
import com.daiba.firm.service.FirmService;
import com.daiba.place.service.PlaceService;
import com.daiba.user.model.User;
import com.daiba.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daiba.utils.JSONUtils.objectMapper;

/**
 * Created by dolphinzhou on 2016/10/25.
 * <p>
 * 公众号菜单-带单
 */
@Controller
@RequestMapping("/WeiXin/bringer")
public class BringerController extends BaseController {

    @Autowired
    private FirmService firmService;

    @Autowired
    private PlaceService placeService;

    @Autowired
    private UserService userService;

    @Autowired
    private DishesService dishesService;

    @RequestMapping(value = "/bringerOrder", method = {RequestMethod.GET})
    public String bringerOrder(HttpServletRequest request, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (isLogin(session)) {
            return "wx/bringerSubs/bringpagelist";
        } else {
            session.setAttribute("state", "410");
            return "redirect:/WeiXin/login";
        }
    }

    /**
     * 带单页面获取带客所有订单
     * 该页面需获取session，还需判定该用户是否为带客
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderList.do", method = {RequestMethod.POST})
    @ResponseBody
    public String orderList(HttpServletRequest request, HttpSession session) {
        List<Firm> firmUserList = null;
        User user = (User) session.getAttribute("user");
        if ("带客".equals(user.getRole())) {
            int briId = user.getBriId();
            int orderState = Integer.parseInt(request.getParameter("orderState"));
            firmUserList = firmService.showBringerAllFirm(briId, orderState);
        }
        try {
            System.out.println(objectMapper.writeValueAsString(firmUserList));
            return objectMapper.writeValueAsString(firmUserList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取带单详情(发单人+订单流水线详情+订单详情)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/showBringerFirmDetail.do", method = {RequestMethod.GET})
    @ResponseBody
    public ModelAndView showBringerFirmDetail(HttpServletRequest request) {
        //订单详情页面
        String viewName = "wx/bringerSubs/bringOrder_info";
        ModelAndView modelAndView = new ModelAndView(viewName);
        //获取订单流水线id
        String firmId = request.getParameter("firmId");
//        //获取订单地址码
//        String acceptAddCode = request.getParameter("acceptAddCode");
        //获取firm详情
        Firm firmDetail = firmService.getFirmDetail(firmId);
        //购物列表
        List<Map<String, Object>> cartsList = null;
        if (firmDetail.getOrder().getStaId() == 31) {
            cartsList = dishesService.getCarts(firmId);
        }
//        //获取三级拼接地址
//        String orderDetailAdd = placeService.getPlace3NameById(acceptAddCode);
        //获取发单人简要信息
        int userId = firmDetail.getUser().getUserId();
        User sendUser = userService.getSendUserInfo(userId);

        //System.out.println(getUserInfo(request).getBriId());
        //System.out.println(firmDetail.getBriId());

        if (getUserInfo(request).getBriId() != firmDetail.getBriId()) {
            firmDetail = null;
            sendUser = null;
            cartsList = null;
        }

        try {
            modelAndView.addObject("firmDetail", objectMapper.writeValueAsString(firmDetail));
//            modelAndView.addObject("orderDetailAdd", objectMapper.writeValueAsString(orderDetailAdd));
            modelAndView.addObject("sendUser", objectMapper.writeValueAsString(sendUser));
            modelAndView.addObject("cartsList", objectMapper.writeValueAsString(cartsList));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return modelAndView;
    }

    /**
     * 带客取消订单
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
        System.out.println(firmId);
        User bringer = (User) session.getAttribute("user");
        int flag = firmService.cancelFirmByBringer(firmId, bringer.getPhoneNum());
        cancelMessages.put("cancelMessage", flag);
        try {
            return objectMapper.writeValueAsString(cancelMessages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 确认送达按钮
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/confirmAcceptFirm.do", method = {RequestMethod.POST})
    @ResponseBody
    public String confirmAcceptFirm(HttpServletRequest request) {
        Map<String, Object> confirmMessages = new HashMap<String, Object>();
        User userInfo = (User) request.getSession().getAttribute("user");
        String firmId = request.getParameter("firmId");

        List<Map<String,Object>> firmMerchantMessages = dishesService.getMerchantMessage(firmId);
        System.out.println("------------------");
        System.out.println(firmMerchantMessages);
        System.out.println("------------------");

        int flag = firmService.confirmFirm(firmMerchantMessages,firmId, userInfo.getBriId(), userInfo.getOpenId());
        confirmMessages.put("confirmMessage", flag);

        try {
            return objectMapper.writeValueAsString(confirmMessages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
