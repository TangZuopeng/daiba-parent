package com.daiba;

import com.daiba.user.model.User;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
 *18443187713
 * @author　 penghaitao
 * @date　 2016-09-17  12:39
 * @description　 $
 */
@Controller
public class BaseController {

    //当发生异常的时候，用来返回异常信息
    protected String errMsg = "{\"success\":false, \"message\":\"操作失败，请重试\"}";// 错误信息
    protected String succMsg = "{\"success\":true, \"message\":\"操作成功\"}";// 成功信息

    /*
     * 登录判定
     */
    protected Boolean isLogin(HttpSession session) {
//        System.out.println(session.getAttribute("ISONLINE"));
        //return true;
        if (session.getAttribute("ISONLINE") == null) {
            return false;
        }
        return (Boolean) session.getAttribute("ISONLINE");
    }

    protected Boolean isWX(HttpSession session) {
//        session.setAttribute("OPENID","123");
        if (session.getAttribute("OPENID") == null) {
            return false;
        }
        return true;
    }

    /**
     * 自定义错误信息
     * @param text
     * @return str 错误信息
     * @author　 qiuyong
     */
    public String createErrorMsg(String text) {
        return "{\"success\":false, \"message\":\"" + text + "\"}";
    }

    /**
     * 获取存储到session的用户信息
     *
     * @param request
     * @return
     * @author　 qiuyong
     */
    public User getUserInfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user;
    }

    /**
     * 获取存储在session的用户id号
     *
     * @param request
     * @return 返回用户id号
     */
    public int getUserId(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user.getUserId();
    }
}
