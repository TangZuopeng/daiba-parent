package com.daiba.admin.model;

import java.util.Date;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
public class Admin {
    private int adminId;
    /**
     * 账户
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 管理员姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phoneNum;

    /**
     * 微信号
     */
    private String wx;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 最近登录时间
     */
    private Date recetlyLoginTime;

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int id) {
        this.adminId = adminId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRecetlyLoginTime() {
        return recetlyLoginTime;
    }

    public void setRecetlyLoginTime(Date recetlyLoginTime) {
        this.recetlyLoginTime = recetlyLoginTime;
    }

    public Admin() {
    }

    public Admin(int adminId, String account, String password, String name, String phoneNum, String wx, String qq, String email, Date recetlyLoginTime) {
        super();
        this.adminId = adminId;
        this.account = account;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.wx = wx;
        this.qq = qq;
        this.email = email;
        this.recetlyLoginTime = recetlyLoginTime;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", wx='" + wx + '\'' +
                ", qq='" + qq + '\'' +
                ", email='" + email + '\'' +
                ", recetlyLoginTime=" + recetlyLoginTime +
                '}';
    }
}
