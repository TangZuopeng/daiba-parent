package com.daiba.user.service;


import com.daiba.user.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User 的Service业务逻辑层接口
 * Created by dolphinzhou on 2016/9/27.
 */
public interface UserService {
    /**
     * 注册用户
     *
     * @param user
     * @return int值 0-重复提交 1-注册成功 2-手机号码已经注册 3-验证码错误 4-该微信已经注册过手机号码
     */
    public int register(User user, HttpServletRequest request);

    /**
     * 手机号码是否已经存在
     *
     * @param phoneNum
     * @return
     */
    public boolean hasPhoneNum(String phoneNum);

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @return 1-登录成功 2-手机号码未注册 3-手机号码或密码不对
     */
    public int login(HttpServletRequest request, HttpServletResponse response);

    /**
     * 忘记密码
     *
     * @return 0-重复提交 1-找回成功 2-手机号码未注册 3-验证码错误
     */
    public int forgetPassword(HttpServletRequest request);

    /**
     * 修改手机号码
     *
     * @param phoneNum
     * @return 0-重复提交 1-修改成功 2-手机号码未注册 3-原密码错误
     */
    public int changerPassword(String phoneNum, HttpServletRequest request);

    /**
     * 根据发单人ID 获取发单人简要信息
     *
     * @param id
     * @return
     */
    public User getSendUserInfo(int id);

    /**
     * 根据带客 ID 获取接单人简要信息
     *
     * @param bringerId
     * @return
     */
    public User getAcceptUserInfo(int bringerId);


    /**
     * 发单成功之后，用户发单数+1，金额+
     *
     * @param userId
     * @param spending
     */
    public void sendOrderSuccess(int userId, int spending);

    /**
     * 根据openId获取用户
     *
     * @param openId
     * @return
     */
    public User getUserInfoByOpenId(String openId);

    /**
     * 更新最近登录时间
     *
     * @param user
     */
    public void updateUserState(User user);

    /**
     * 获取所有用户的openId
     *
     * @return
     */
    public List<String> getAllOpenId(String role);

    public List<User> getAllUserInfo();

    public User getUserInfoByBriId(int briId);

}
