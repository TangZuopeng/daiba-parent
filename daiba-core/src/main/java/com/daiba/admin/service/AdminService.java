package com.daiba.admin.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
public interface AdminService {
    /**
     * 管理员登录
     *
     * @param request
     * @return 1-登录成功 2-账户不存在 3-账号或密码不正确
     */
    public int login(HttpServletRequest request);

}
