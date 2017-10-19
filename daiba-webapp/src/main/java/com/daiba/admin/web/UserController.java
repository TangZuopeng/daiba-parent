package com.daiba.admin.web;

import com.daiba.admin.base.AdminBaseController;
import com.daiba.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/12/11.
 */
@RequestMapping(value = "/Admin")
@Controller
public class UserController extends AdminBaseController {

    @Autowired
    private UserService userService;
    //
    //@RequestMapping(value = "/getUserInfoList", method = {RequestMethod.POST})
    //@ResponseBody
    //public PageResponse<User> findAllUserInfo(User user){
    //    return new PageResponse<User>(user.getPage(),userService.getAllUserInfo());
    //}
}
