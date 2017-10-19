package com.daiba.user.service.impl;

import com.daiba.user.dao.PersonalDao;
import com.daiba.user.model.ConsigneeMessage;
import com.daiba.user.model.Qualification;
import com.daiba.user.model.User;
import com.daiba.user.service.PersonalService;
import com.daiba.utils.SendMessage;
import com.daiba.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qiuyong
 * @date 2016/9/28.
 * @declare 快递之个人中心模块
 */
@Service
public class PersonalServiceImpl implements PersonalService{
    @Autowired
    PersonalDao personalDao;
//快递之个人中心_地址管理部分
    public Map<String, Object> addDeliveryAddress(ConsigneeMessage consigneeMessage) throws Exception {
        Map<String,Object> rtnMap=new HashMap<String, Object>();
        personalDao.addDeliveryAddress(consigneeMessage);
        rtnMap.put("success",true);
        rtnMap.put("message","收货地址添加成功!");
        return rtnMap;
    }

    @Override
    public Map<String, Object> showDeliveryAddresses(int useId) throws Exception {
        Map<String,Object> rtnMap=new HashMap<String, Object>();
        List<ConsigneeMessage> ls=personalDao.getAddressesByUserId(useId);
        if (null!=ls&&ls.size()>0){
            rtnMap.put("addresses",ls);
            rtnMap.put("success",true);
            rtnMap.put("message","收货地址查询成功!");
            return rtnMap;
        }
        rtnMap.put("success",false);
        rtnMap.put("message","抱歉,没查找到你需要的收货地址信息");
        return rtnMap;
    }


    public Map<String, Object> deleteDeliveryAddress(int conmessageId) throws Exception {
        Map<String,Object> rtnMap=new HashMap<String, Object>();
        personalDao.deleteAddressByConmsgId(conmessageId);
        rtnMap.put("success",true);
        rtnMap.put("message","收货地址删除成功!");
        return rtnMap;
    }

    public Map<String, Object> editDeliveryAddress(ConsigneeMessage consigneeMessage) throws Exception {
        Map<String,Object> rtnMap=new HashMap<String, Object>();
        personalDao.editAddress(consigneeMessage);
        rtnMap.put("success",true);
        rtnMap.put("message","收货地址修改成功!");
        return rtnMap;
    }

    @Override
    public Map<String, Object> showDeliveryAddress(int conmessageId) throws Exception {
        Map<String,Object> rtnMap=new HashMap<String, Object>();
        ConsigneeMessage conmessage=personalDao.getAddressByConmsgId(conmessageId);
        if(null!=conmessage){
            rtnMap.put("conmessage",conmessage);
            rtnMap.put("success",true);
            rtnMap.put("message","个人中心的收货地址查询成功");
            return rtnMap;
        }
        rtnMap.put("success",false);
        rtnMap.put("message","该用户地址不存在!");
        return rtnMap;
    }

//快递之个人中心_个人详细信息
    @Override
    public Map<String, Object> showDetailedUserInfo(int userId,HttpServletRequest request) throws Exception {
        Map<String,Object> rtnMap=new HashMap<String, Object>();
        User user=personalDao.findUserByUserId(userId);
        if(null!=user){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("name",user.getName());//用户昵称
            map.put("sex",user.getSex());//用户性别
            map.put("phoneNum",user.getPhoneNum());//获取用户手机号码
            map.put("role",user.getRole());
            map.put("portrait", user.getPortrait());//用户头像
            System.out.println(StringUtil.subPhysiclPathToUrl(user.getPortrait(),request));
            rtnMap.put("success",true);
            if (!"普通用户".equals(user.getRole())){
                Qualification qualification=personalDao.getQualificationInfo(user.getUserId());//获取用户的校验信息
                map.put("studentNum",qualification.getStudentNum());
                map.put("realName",qualification.getRealName());
                rtnMap.put("flag",true);
                rtnMap.put("personalInfo",map);
                return rtnMap;
            }else{
                rtnMap.put("flag",false);//用来辨别是不是带客
                rtnMap.put("personalInfo",map);
                return rtnMap;
            }
        }
        rtnMap.put("success",false);
        rtnMap.put("message","服务异常,无法获取到用户信息!");
        return rtnMap;
    }

    @Override
    public Map<String, Object> editUserNickName(Map map) throws Exception {
        Map<String,Object> rtnMap=new HashMap<String, Object>();
        personalDao.editUserNickName(map);
        rtnMap.put("success",true);
        rtnMap.put("message","修改昵称成功!");
        return rtnMap;
    }

    @Override
    public Map<String, Object> editUserPass(int userId, String userPass) throws Exception {
        Map<String,Object> rtnMap=new HashMap<String, Object>();
        User user=personalDao.findUserByUserId(userId);
        if(null!=user){
            rtnMap.put("success",true);
            rtnMap.put("message","密码修改成功!");
        }
        rtnMap.put("success",false);
        rtnMap.put("message","服务异常,无法获取到用户信息!");
        return rtnMap;
    }

    @Override
    public Map<String, Object> editPhoneNum(User user) throws Exception {
        Map<String,Object> rtnMap=new HashMap<String, Object>();
        User mUser=personalDao.findUserByUserId(user.getUserId());
        if(user.getPassword().equals(mUser.getPassword())){

        }
        return null;
    }

    @Override
    public Qualification getQualificationInfo(int userId) throws Exception {
        return personalDao.getQualificationInfo(userId);
    }

    @Override
    public Map<String, Object> editSex(int userId, int sexCode) throws Exception {
        Map<String ,Object> resultMap=new HashMap<String,Object>();
        personalDao.editSex(userId,sexCode);
        resultMap.put("success",true);
        resultMap.put("message","用户性别修改成功!");
        return resultMap;
    }

    @Override
    public Map<String, Object> editIsReceiver(int userId, int isReceiver) {
        Map<String ,Object> resultMap=new HashMap<String,Object>();
        personalDao.editIsReceiver(userId,isReceiver);
        if (isReceiver == 1){
            resultMap.put("isReceiver",true);
        } else {
            resultMap.put("isReceiver",false);
        }
        return resultMap;
    }

    @Override
    public String getBringerRealName(int briId) {
        return personalDao.selectBringerRealName(briId);
    }

    //个人中心_快递简要信息
    @Override
    public Map<String, Object> showUserInfo(int userId,HttpServletRequest request) throws Exception {
        Map<String,Object> rtnMap=new HashMap<String, Object>();
        User user=personalDao.findUserByUserId(userId);
        if(null!=user){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("name",user.getName());//用户昵称
            map.put("portrait", user.getPortrait());//用户头像
            map.put("orderNum",user.getOrderNum());//用户发单数
            rtnMap.put("success",true);
            if (!"普通用户".equals(user.getRole())){
                Qualification qualification=personalDao.getQualificationInfo(user.getUserId());
                int bringerId=qualification.getBriId();
                Map<String,Object> bringerMap = personalDao.getBringerInfo(bringerId);
                map.put("creditWorthiness",bringerMap.get("creditworthiness"));//带客信誉度
                map.put("acceptCount",bringerMap.get("acceptCount"));//带客已接单数目
                map.put("weekIncome",bringerMap.get("weekIncome"));//带客本周收入
                map.put("isReceiver",bringerMap.get("isReceiver"));//是否接收新订单通知
                rtnMap.put("personalInfo",map);
                rtnMap.put("flag",true);//用来标志是否为带客
                return rtnMap;
            }else{
                rtnMap.put("personalInfo",map);
                rtnMap.put("flag",false);
                return rtnMap;
            }
        }
        rtnMap.put("success",false);
        rtnMap.put("message","服务异常,无法获取到用户信息!");
        return rtnMap;
    }

    @Override
    public Map<String, Object> applyBringer(Map map, String phoneNum) throws Exception {
        Map<String,Object> rtnMap=new HashMap<String, Object>();
        /**
         *1、若业务审核表没有记录代表未申请
         *2、若业务审核表记录大于1 并且bringerid>0 代表已审核通过
         *3、若业务审核表记录大于1 并且bringerid=0 代表正在审核中
         */
        if(personalDao.isApplyExist((Integer)map.get("useId"))>0){
            if(null!=personalDao.getQualificationInfo((Integer) map.get("useId")).getBriId()+""){
                rtnMap.put("success",false);
                rtnMap.put("message","您已经发出申请，请尽快填好信息！");
                return rtnMap;
            }
            rtnMap.put("success",false);
            rtnMap.put("message","该用户已经申请,请等待反馈信息!");
            return rtnMap;
        }
        personalDao.applyBringer(map);
        rtnMap.put("success", true);
        rtnMap.put("message","申请带客已受理,择日查看审核结果");
        org.json.JSONObject jsonObject = SendMessage.applyBringerInform("有用户想要成为带客！", phoneNum, "带客", "请尽快联系该用户！");
        SendMessage.send(SendMessage.ZHANG, SendMessage.APPLY_BRINGER_TEMPLAT, "", jsonObject);
        SendMessage.send(SendMessage.FANG, SendMessage.APPLY_BRINGER_TEMPLAT, "", jsonObject);
        return rtnMap;
    }



    @Override
    public User findUser(int userId) throws Exception {
        return personalDao.findUserByUserId(userId);
    }




}
