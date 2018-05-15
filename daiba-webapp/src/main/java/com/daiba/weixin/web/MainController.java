package com.daiba.weixin.web;

import com.daiba.BaseController;
import com.daiba.firm.model.Firm;
import com.daiba.firm.service.FirmService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.daiba.utils.JSONUtils.objectMapper;

/**
 * @author　 TinyDolphin
 * @date　 2016-09-12  19:10
 * @description　 微信主界面
 */
@Controller
@RequestMapping("/WeiXin/main")
public class MainController extends BaseController {

    @Autowired
    private FirmService firmService;

    @RequestMapping(value = "/home", method = {RequestMethod.GET})
    public String home(HttpSession session) {

        //设置session不活动时间为30分
        session.setMaxInactiveInterval(60 * 30);

        if (isWX(session)) {
            return "wx/main/home";
        } else {
            //没有使用带吧微信公众平台
            return "error/authorize_error";
        }
    }

    @RequestMapping(value = "/order", method = {RequestMethod.GET})
    public String order(HttpSession session) {
        if (isWX(session)) {
            if (isLogin(session)) {
                return "wx/subpages/subpagelist";
            } else {
                session.setAttribute("state", "210");
                return "redirect:/WeiXin/login";
            }
        } else {
            //没有使用带吧微信公众平台
            return "error/authorize_error";
        }
    }

    @RequestMapping(value = "/mine", method = {RequestMethod.GET})
    public String mine(HttpSession session) {
        if (isWX(session)) {
            if (isLogin(session)) {
                return "wx/main/mine";
            } else {
                session.setAttribute("state", "310");
                return "redirect:/WeiXin/login";
            }
        } else {
            //没有使用带吧微信公众平台
            return "error/authorize_error";
        }
    }

    /**
     * 首页显示所有快递订单(未接单)
     * OR
     * 下拉刷新：无论多少未接单，都返回前7条数据(包括未接单、已接单、已完成)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getFirm.do", method = {RequestMethod.POST})
    @ResponseBody
    public List<Firm> getFirm(HttpServletRequest request) {

        int count = Integer.parseInt(request.getParameter("count"));
        int orderType = Integer.parseInt(request.getParameter("orderType"));
        //表示：初始化加载和下拉刷新
        List<Firm> firmList = firmService.showAllFirm(request.getParameter("campus") + "%", orderType, count);
        return firmList;
    }

    /**
     * 订单页面获取用户所有订单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderList.do", method = {RequestMethod.POST})
    @ResponseBody
    public String orderList(HttpServletRequest request) {
        int useId = Integer.parseInt(request.getParameter("useId"));
        int orderState = Integer.parseInt(request.getParameter("orderState"));
        List<Firm> firmUserList = firmService.showUserAllFirm(useId, orderState);
        try {
//            System.out.println(objectMapper.writeValueAsString(firmUserList));
            return objectMapper.writeValueAsString(firmUserList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
