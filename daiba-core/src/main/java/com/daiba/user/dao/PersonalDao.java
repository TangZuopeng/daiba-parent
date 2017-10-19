package com.daiba.user.dao;

import com.daiba.mybatis.MyBatisScan;
import com.daiba.user.model.ConsigneeMessage;
import com.daiba.user.model.Qualification;
import com.daiba.user.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by qiuyong on 2016/9/28.
 */
@MyBatisScan
public interface PersonalDao {

    /**
     * 快递_个人中心 地址管理
     */
    public void addDeliveryAddress(ConsigneeMessage consigneeMessage); //新增收货地址

    public List<ConsigneeMessage> getAddressesByUserId(int useId);//通过用户id号获得收货地址

    public ConsigneeMessage getAddressByConmsgId(int configId);//通过地址id号获取地址信息

    public void editAddress(ConsigneeMessage consigneeMessage);//修改收货地址

    public void deleteAddressByConmsgId(int conmsgId);  //删除收货地址通过 地址ID号删除

    /**
     * 快递_个人中心 用户信息管理
     */
    public User findUserByUserId(int userId);//通过用户id获取用户信息

    public void editUserNickName(Map<String, Object> map);//修改用户昵称

    public void applyBringer(Map<String, Object> map);//受理申请为带客请求

    public int isApplyExist(int useId);//用来判断带客是否已经申请

    public Qualification getQualificationInfo(int useId);//获取认证信息

    public Map<String, Object> getBringerInfo(int bringerId);//获取带客相关信息

    public void editSex(@Param("userId") int userId, @Param("sexCode") int sexCode);//修改用户性别

    public void editIsReceiver(@Param("userId") int userId, @Param("isReceiver") int isReceiver);//开启or关闭新订单推送

    /**
     * 根据带客ID获取带客真实姓名
     *
     * @param briId
     * @return
     */
    public String selectBringerRealName(@Param("briId") int briId);
}