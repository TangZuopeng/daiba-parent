package com.daiba.user.service;

import com.daiba.user.model.ConsigneeMessage;
import com.daiba.user.model.Qualification;
import com.daiba.user.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * Created by qiuyong on 2016/9/28.
 */
public interface PersonalService {
//地址管理页
    /**
     * @declare  添加 快递收货的具体地址
     * @param consigneeMessage
     * @return map
     */
    public Map<String,Object> addDeliveryAddress(ConsigneeMessage consigneeMessage) throws Exception;

    /**
     * @declare 显示个人收货详细地址列表
     * @param useId 用户id号
     * @return map
     */
    public Map<String,Object> showDeliveryAddresses(int useId)throws Exception;

    /**
     * @declare 删除指定的收货地址
     * @param conmsgId
     * @return map
     */
    public Map<String,Object> deleteDeliveryAddress(int conmsgId)throws Exception;

    /**
     * @declare 根据收货地址id号修改收货地址
     * @param consigneeMessage
     * @return map
     */
    public Map<String,Object> editDeliveryAddress(ConsigneeMessage consigneeMessage)throws Exception;

    public Map<String,Object> showDeliveryAddress(int conmsgId)throws Exception;

//个人中心页
    /**
     * 查询快递_用户个人简要信息_处理 已收单数
     * @param userId 用户id号
     * @return
     * @throws Exception
     */
    public Map<String,Object> showUserInfo(int userId,HttpServletRequest request)throws Exception;

    /**
     * @declare  申请为带客，修改状态
     * @param map key值分别为 int useId(用户id号)  int userStatus (用户认证状态) 20 21 22 未受理  审核中 认证成功
     * @return
     * @throws Exception
     */
    public Map<String,Object> applyBringer(Map map, String phoneNum) throws Exception;

    /**
     * 通过用户id号 查询用户
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    public User findUser(int userId) throws Exception;

    /**
     * 通过用户id号 查看快递用户的详细信息
     * @param userId 用户id
     * @return
     * @throws Exception
     */
    public Map<String,Object> showDetailedUserInfo(int userId,HttpServletRequest request) throws Exception;

    /**
     * @declare 修改个人中心的昵称
     * @param map  key值分别为 useId(用户id号)   name(用户昵称)
     * @return
     */
    public Map<String,Object> editUserNickName(Map map ) throws Exception;

    /**
     *  修改个人信息的用户密码
     * @param userId 用户id号
     * @param userPass 用户密码
     * @return
     * @throws Exception
     */
    public Map<String,Object> editUserPass(int userId,String userPass)throws Exception;

    /**
     * 修改个人中心手机号
     * @param user  userId 用户id号   phoneNum 用户手机号
     * @return
     * @throws Exception
     */
    public Map<String,Object> editPhoneNum(User user) throws Exception;

    /**
     * 获取用户认证信息
     * @param userId 用户id号
     * @return Qualification 认证信息
     * @throws Exception
     */
    public Qualification getQualificationInfo(int userId) throws Exception;

    public Map<String,Object> editSex(int userId,int sexCode) throws Exception;

    public Map<String,Object> editIsReceiver(int userId,int isReceiver);

    /**
     * 获取带客的真实姓名
     * @param briId
     * @return
     */
    public String getBringerRealName(int briId);











   



}
