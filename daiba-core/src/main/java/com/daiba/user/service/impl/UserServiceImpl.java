package com.daiba.user.service.impl;

import com.daiba.global.Constants;
import com.daiba.user.dao.QualificationDao;
import com.daiba.user.dao.UserDao;
import com.daiba.user.model.Address;
import com.daiba.user.model.User;
import com.daiba.user.service.UserService;
import com.daiba.utils.MD5;
import com.daiba.utils.OperationFileUtil;
import com.daiba.utils.StringUtil;
import com.daiba.utils.TokenProccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * User 的Service业务逻辑层接口实现
 * Created by dolphinzhou on 2016/9/27.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private QualificationDao qualificationDao;

    //@Autowired
    //private InvitationDao invitationDao;

    @Override
    public int register(User user, HttpServletRequest request) {

        String code = request.getParameter("code");

        String serviceCode = (String) request.getSession().getAttribute("code");
        String codePhoneNum = (String) request.getSession().getAttribute("codePhoneNum");
        //获取session中微信的openId、昵称、头像url地址和性别
        String openId = (String) request.getSession().getAttribute("OPENID");
        String nickName = (String) request.getSession().getAttribute("NICKNAME");
        String headImgUrl = (String) request.getSession().getAttribute("HEADIMGURL");
        Integer sex = (Integer) request.getSession().getAttribute("SEX");

        //邀请码使用之后状态为 1
        //String useStatue = "1";
        //String invitationCode = (String) request.getSession().getAttribute("invitationCode");

        if (!TokenProccessor.getInstance().isRepeatSubmit(request)) {
            //校验手机号码是否已经注册
            if (hasPhoneNum(user.getPhoneNum())) {
                return 2;
            } else {
                //检验验证码（同时校验该验证码是否是该手机号码接收的）
                if (codePhoneNum.equals(user.getPhoneNum()) && serviceCode.equals(code)) {
                    init(user, nickName, headImgUrl, sex, openId, request);
                    //MD5(手机号码+MD5(密码))
                    user.setPassword(MD5.encryption(user.getPhoneNum() + user.getPassword()));
                    userDao.insert(user);
                    //使邀请码失效
                    //invitationDao.update(invitationCode, useStatue);
                    request.getSession().removeAttribute("token");
                    return 1;
                } else {
                    return 3;
                }
            }
        } else {
            return 0;
        }
    }

    @Override
    public boolean hasPhoneNum(String phoneNum) {
        String id = userDao.selectIdByPhoneNum(phoneNum);
        if (id == null) {
            return false;
        } else {
            //System.out.println("手机号码已经存在！！！");
            return true;
        }
    }

    @Override
    public int login(HttpServletRequest request, HttpServletResponse response) {
        String phoneNum = request.getParameter("phoneNum");
        String password = request.getParameter("password");

        User user = userDao.selectByPhoneNum(phoneNum);
        if (user != null) {
            //注册手机号码已注册，可以登录
            //MD5(手机号码+MD5(密码))
            password = MD5.encryption(phoneNum + password);
            if (phoneNum.equals(user.getPhoneNum()) && password.equals(user.getPassword())) {
                //手机号码和密码正确
                //登录成功
                //将用户信息、在线状态 信息 存入session
                updateUserState(user);
                //登录成功将用户实体和在线状态存入session
                int bri_id = user.getBriId();
                if(bri_id!=0){
                    Address address = qualificationDao.selectAddressByBriId(bri_id);
                    request.getSession().setAttribute("address", address);
                }
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("ISONLINE", true);
                return 1;
            } else {
                //手机号码或密码不对
                return 3;
            }
        } else {
            //手机号码未注册
            return 2;
        }
    }

    @Override
    public int forgetPassword(HttpServletRequest request) {

        String phoneNum = request.getParameter("phoneNum");
        String newPassword = request.getParameter("newPassword");
        String code = request.getParameter("code");
        String serviceCode = (String) request.getSession().getAttribute("code");
        String codePhoneNum = (String) request.getSession().getAttribute("codePhoneNum");

        if (!TokenProccessor.getInstance().isRepeatSubmit(request)) {
            User user = userDao.selectByPhoneNum(phoneNum);
            //该手机号码已经注册
            if (user != null) {
                //检验验证码
                if (codePhoneNum.equals(user.getPhoneNum()) && serviceCode.equals(code)) {
                    //MD5(手机号码+MD5(密码))
                    user.setPassword(MD5.encryption(phoneNum + newPassword));
                    userDao.updateUserInfo(user);
                    request.getSession().removeAttribute("token");
                    //找回成功
                    return 1;
                } else {
                    //验证码错误
                    return 3;
                }
            } else {
                //手机号码未注册
                return 2;
            }
        } else {
            return 0;
        }

    }

    @Override
    public int changerPassword(String phoneNum, HttpServletRequest request) {

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        User user = userDao.selectByPhoneNum(phoneNum);
        String password = MD5.encryption(phoneNum + oldPassword);

        if (!TokenProccessor.getInstance().isRepeatSubmit(request)) {
            //该手机号码已经注册
            if (user != null) {
                if (password.equals(user.getPassword())) {
                    //MD5(手机号码+MD5(密码))
                    user.setPassword(MD5.encryption(phoneNum + newPassword));
                    userDao.updateUserInfo(user);
                    request.getSession().removeAttribute("token");
                    //修改成功
                    return 1;
                } else {
                    //原密码错误
                    return 3;
                }
            } else {
                //手机号码未注册
                return 2;
            }
        } else {
            return 0;
        }
    }

    @Override
    public User getSendUserInfo(int id) {
        return userDao.selectSendUserInfo(id);
    }

    @Override
    public User getAcceptUserInfo(int bringerId) {
        return userDao.selectAcceptUserInfo(bringerId);
    }

    @Override
    public void sendOrderSuccess(int userId, int spending) {
        userDao.updateUserOrderNumAndSpending(userId, spending);
    }

    @Override
    public User getUserInfoByOpenId(String openId) {
        return userDao.selectUserByOpenId(openId);
    }

    /**
     * 初始化用户信息
     *
     * @param user
     * @param nickName   微信昵称
     * @param headImgUrl 微信头像网络地址
     * @param sex        微信性别
     */
    private void init(User user, String nickName, String headImgUrl, int sex, String openId, HttpServletRequest request) {
        //下载wx头像，作为默认头像
        String wxPortraitUrl = headImgUrl;
        if (wxPortraitUrl == null) {
            wxPortraitUrl = "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3088020551,4198140884&fm=96";
        }
        String filePath = Constants.USER_PORTRAIT_PATH + user.getPhoneNum() + File.separator;
        String fileName = UUID.randomUUID().toString();
        String file = OperationFileUtil.uploadImage(wxPortraitUrl, filePath, fileName);

        nickName = nickName.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        nickName = nickName.replaceAll("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]", "");

        if(nickName.contains("高校跑腿君")){
            nickName = nickName.replaceAll("高校跑腿君","XXXXX");
        }
        if(nickName.contains("希必地")){
            nickName = nickName.replaceAll("希必地","XXX");
        }

        if (nickName == null) {
            user.setName(user.getPhoneNum());
        } else {
            user.setName(nickName);
        }
        user.setPortrait(StringUtil.subPhysiclPathToUrl(file));
        user.setRegisterTime(new Date());
        user.setRecetlyLoginTime(user.getRegisterTime());
        user.setOpenId(openId);

        if (sex == 0) {
            //如果没有性别就默认为男:1
            user.setSex(1);
        } else {
            user.setSex(sex);
        }
    }

    /**
     * 更新用户最近登录时间
     *
     * @param user
     */
    @Override
    public void updateUserState(User user) {
        user.setRecetlyLoginTime(new Date());
        userDao.updateUserInfo(user);
    }

    @Override
    public List<String> getAllOpenId(String role) {
        return userDao.selectAllOpenId(role);
    }

    @Override
    public List<User> getAllUserInfo() {
        return userDao.selectAllUserInfo();
    }

    @Override
    public User getUserInfoByBriId(int briId) {
        return userDao.selectUserByBriId(briId);
    }

}
