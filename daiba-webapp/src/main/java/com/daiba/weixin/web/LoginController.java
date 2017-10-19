package com.daiba.weixin.web;

import com.daiba.BaseController;
import com.daiba.invitation.service.InvitationService;
import com.daiba.user.model.User;
import com.daiba.user.service.UserService;
import com.daiba.utils.TokenProccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static com.daiba.utils.JSONUtils.objectMapper;

/**
 * 登录、注册、忘记密码控制器
 * Created by dolphinzhou on 2016/9/27.
 */
@Controller
@RequestMapping("/WeiXin")
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private InvitationService invitationService;

    @RequestMapping(value = "/register", method = {RequestMethod.GET})
    public String register(HttpSession session, HttpServletRequest request) {
        if (isWX(session)) {
            //生成防止重复提交的令牌
            String token = TokenProccessor.getInstance().makeToken();//创建令牌
            request.getSession().setAttribute("token", token);   ///在服务器使用session保存token(令牌)
            return "wx/login/register";
        } else {
            //没有使用带吧微信公众平台
            return "error/authorize_error";
        }
    }

    /**
     * 处理注册操作
     *
     * @param user
     * @param request
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register.do", method = {RequestMethod.POST})
    public String registerPost(User user, HttpServletRequest request, HttpSession session) {
        Map<String, Object> regMessages = new HashMap<String, Object>();
        int flag = userService.register(user, request);
        if (flag == 1) {
            //移除session中的验证码code、接收到验证码的手机号码
            session.removeAttribute("code");
            session.removeAttribute("codePhoneNum");
            ////移除session中的邀请码invitationCode
            //session.removeAttribute("invitationCode");
        }
        regMessages.put("regMessage", flag);
        try {
            return objectMapper.writeValueAsString(regMessages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public String login(HttpServletRequest request) {
        if (isWX(request.getSession())) {
            return "wx/login/login";
        } else {
            //没有使用带吧微信公众平台
            return "error/authorize_error";
        }
    }

    /**
     * 处理登录操作
     *
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "/login.do", method = {RequestMethod.POST})
    @ResponseBody
    public String loginPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        //用于判断是从哪里跳转过来的？要跳转到那里去？
        String state = (String) session.getAttribute("state");
        Map<String, Object> loginMessages = new HashMap<String, Object>();
        int flag = userService.login(request,response);
        loginMessages.put("loginMessage", flag);

        if (flag == 1 && "410".equals(state)) {
            User user = (User) session.getAttribute("user");
            if (user.getRole().equals("普通用户")) {
                state = "4101";
            }
        }

        //方便前台跳转
        loginMessages.put("stateMessage", state);
        session.removeAttribute("state");

        try {
            return objectMapper.writeValueAsString(loginMessages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/forgetPassword", method = {RequestMethod.GET})
    public String forgetPassword(HttpSession session, HttpServletRequest request) {
        if (isWX(session)) {
            //生成防止重复提交的令牌
            String token = TokenProccessor.getInstance().makeToken();//创建令牌
            request.getSession().setAttribute("token", token);   ///在服务器使用session保存token(令牌)
            System.out.println("forgetPassword:" + token);
            return "wx/login/forget_password";
        } else {
            //没有使用带吧微信公众平台
            return "error/authorize_error";
        }
    }

    /**
     * 处理忘记密码
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/forgetPassword.do", method = {RequestMethod.POST})
    @ResponseBody
    public String forgetPasswordPost(HttpServletRequest request, HttpSession session) {
        Map<String, Object> forgetMessages = new HashMap<String, Object>();
        int flag = userService.forgetPassword(request);
        if (flag == 1) {
            session.removeAttribute("code");
            session.removeAttribute("codePhoneNum");
        }
        forgetMessages.put("forgetMessage", flag);
        try {
            return objectMapper.writeValueAsString(forgetMessages);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    ///**
    // * 处理邀请码验证
    // *
    // * @param request
    // * @param session
    // * @return
    // */
    //@RequestMapping(value = "/invitationTesting.do", method = {RequestMethod.POST})
    //@ResponseBody
    //public String invitationTestingPost(HttpServletRequest request, HttpSession session) {
    //    Map<String, Object> invitationMessages = new HashMap<String, Object>();
    //
    //    String invitationCode = request.getParameter("invitationCode");
    //
    //    int flag = invitationService.useInvitationCode(invitationCode);
    //
    //    if (flag == 1) {
    //        //方便在注册成功之后，改变该邀请码的状态
    //        session.setAttribute("invitationCode", invitationCode);
    //    }
    //    invitationMessages.put("invitationMessage", flag);
    //    try {
    //        return objectMapper.writeValueAsString(invitationMessages);
    //    } catch (JsonProcessingException e) {
    //        throw new RuntimeException(e);
    //    }
    //}

    @RequestMapping(value = "/userAgreement", method = {RequestMethod.GET})
    public String userAgreement(HttpSession session, HttpServletRequest request) {
        if (isWX(session)) {
            return "wx/login/user_agreement";
        } else {
            //没有使用带吧微信公众平台
            return "error/authorize_error";
        }
    }
}
