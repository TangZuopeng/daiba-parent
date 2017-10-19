package com.daiba.user.model;

import com.daiba.firm.model.Firm;
import com.daiba.option.model.Option;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.util.Date;
import java.util.List;

/**
 * Created by dolphinzhou on 2016/9/27.
 */
public class User {
    private int userId;
    /**
     * 手机号,用作登录名
     */
    //@Pattern(regexp = "^1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}$",message = "请输入正确的手机号码！")
    private String phoneNum;
    //@Pattern(regexp = "^([A-Z]|[a-z]|[0-9]){6,16}$",message = "密码是6-16个字符，由数字、字母组成！")
    private String password;
    /**
     * 昵称，默认微信昵称
     */
    //@Pattern(regexp = "^[\\u4e00-\\u9fa5A-Za-z0-9-_]*${0,10}",message = "限10个字符，支持中英文、数字、减号或下划线")
    private String name;
    /**
     * 头像，默认微信头像
     */
    private String portrait;
    /**
     * 注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date registerTime;
    /**
     * 角色、默认普通用户
     */
    private String role;
    /**
     * 最近登录时间(默认注册时间)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date recetlyLoginTime;
    /**
     * 发单数
     */
    private int orderNum;
    /**
     * 总支出
     */
    @NumberFormat(pattern = "#,###,###")
    private int spending;
    /**
     * 性别
     */
    private int sex;

    /**
     * 带客Id
     */
    private int briId;

    /**
     * 微信端 openId
     */
    private String openId;

    /**
     * 订单流水线(维护 user 表和 firm 表之间的一对多关系)
     */
    private List<Firm> firms;

    /**
     * 意见反馈(维护 user 表和 option 表之间的一对多关系)
     */
    private List<Option> options;

    public List<Firm> getFirms() {
        return firms;
    }

    public void setFirms(List<Firm> firms) {
        this.firms = firms;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getRecetlyLoginTime() {
        return recetlyLoginTime;
    }

    public void setRecetlyLoginTime(Date recetlyLoginTime) {
        this.recetlyLoginTime = recetlyLoginTime;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getSpending() {
        return spending;
    }

    public void setSpending(int spending) {
        this.spending = spending;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getBriId() {
        return briId;
    }

    public void setBriId(int briId) {
        this.briId = briId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public User() {}

    /**
     * 注册账号，默认只需手机号、密码。
     * 其他用户账号信息均为默认值。
     * @param phoneNum
     * @param password
     */
    public User(String phoneNum, String password) {
        this.phoneNum = phoneNum;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", phoneNum='" + phoneNum + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", portrait='" + portrait + '\'' +
                ", registerTime=" + registerTime +
                ", role='" + role + '\'' +
                ", recetlyLoginTime=" + recetlyLoginTime +
                ", orderNum=" + orderNum +
                ", spending=" + spending +
                ", sex=" + sex +
                ", briId=" + briId +
                ", openId='" + openId + '\'' +
                ", firms=" + firms +
                ", options=" + options +
                '}';
    }
}

