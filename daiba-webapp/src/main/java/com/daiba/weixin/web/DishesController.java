package com.daiba.weixin.web;

import com.daiba.BaseController;
import com.daiba.dishes.model.Dishes;
import com.daiba.dishes.service.DishesService;
import com.daiba.merchant.model.Merchant;
import com.daiba.merchant.service.MerchantService;
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
 * com.daiba.weixin.web
 * 带饭界面请求
 *
 * @author TinyDolphin
 *         2017/4/8 17:30.
 */
@Controller
@RequestMapping("/WeiXin/dishes")
public class DishesController extends BaseController {


    @Autowired
    private MerchantService merchantService;

    @Autowired
    private DishesService dishesService;

    @RequestMapping(value = "/enterSubmitDishesOrder", method = RequestMethod.GET, produces = "text/plain;charset=utf-8")
    public String enterSubmitDishesOrder(HttpSession session) {
        if (isLogin(session)) {
            return "wx/main/submit_takeout_order";
        } else {
            return "redirect:/WeiXin/login";
        }
    }

    /**
     * 请求学校校区对应的所有店铺
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMerchants.do", method = {RequestMethod.POST})
    @ResponseBody
    public String getMerchants(HttpServletRequest request) {
        String schoolCode = request.getParameter("schoolCode");
        String campusCode = request.getParameter("campusCode");
        String addTypeCode = request.getParameter("addTypeCode");
        List<Merchant> merchantList = merchantService.showAllMerchant(schoolCode, campusCode, addTypeCode);
        try {
            return objectMapper.writeValueAsString(merchantList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 请求店铺对应的所有菜品
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDishes.do", method = {RequestMethod.POST})
    @ResponseBody
    public String getDishes(HttpServletRequest request) {
        String merchantId = request.getParameter("merchantId");
        List<Dishes> dishesList = dishesService.showAllDishes(merchantId);
        try {
            return objectMapper.writeValueAsString(dishesList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/fullReduction.do", method = {RequestMethod.POST})
    @ResponseBody
    public Integer fullReduction(HttpServletRequest request) {
        Integer goodsMoney = Integer.valueOf(request.getParameter("goodsMoney"));
        return goodsMoney;
    }

}
