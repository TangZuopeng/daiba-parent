package com.daiba.admin.service.impl;

import com.daiba.admin.dao.AdminDao;
import com.daiba.admin.model.Admin;
import com.daiba.admin.service.AdminService;
import com.daiba.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminDao adminDao;

    @Override
    public int login(HttpServletRequest request) {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        Admin admin = adminDao.selectByAccount(account);
        if (admin != null) {
            //账号已注册，可以登录
            //MD5(账号+MD5(密码))
            password = MD5.encryption(account + password);
            if (account.equals(admin.getAccount()) && password.equals(admin.getPassword())) {
                //账号和密码正确
                //登录成功
                admin.setRecetlyLoginTime(new Date());
                adminDao.updateAdminTime(admin);
                //将用户信息、在线状态 信息 存入session
                //登录成功将用户实体和在线状态存入session
                request.getSession().setAttribute("admin", admin);
                request.getSession().setAttribute("ADMINISONLINE", true);
                return 1;
            } else {
                //账号或密码不对
                return 3;
            }
        } else {
            //账号未注册
            return 2;
        }
    }
}
