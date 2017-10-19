package com.daiba.admin.web;

import com.daiba.admin.base.AdminBaseController;
import com.daiba.admin.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.daiba.utils.JSONUtils.objectMapper;

/**
 * Created by dolphinzhou on 2016/12/9.
 */
@Controller
@RequestMapping("/Admin")
public class AdminLoginController extends AdminBaseController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(HttpServletRequest request) {
        //设置session不活动时间为60分
        request.getSession().setMaxInactiveInterval(60*60);
        return "admin/login/login";
    }

    /**
     * 处理登录操作
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/login.do", method = {RequestMethod.POST})
    @ResponseBody
    public String loginPost(HttpServletRequest request,HttpSession session) {
        Map<String, Object> loginMessages = new HashMap<String, Object>();
        int flag = adminService.login(request);
        loginMessages.put("loginMessage", flag);
        try {
            return objectMapper.writeValueAsString(loginMessages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
