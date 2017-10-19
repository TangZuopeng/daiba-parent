package com.daiba.userInfo.model;

import com.genghis.steed.mybatis.model.PageBase;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Tangzuopeng on 2016/12/17.
 */
public class UserInfo extends PageBase {

    private int userId;

    /**
     * 用户手机号
     */
    private String phoneNum;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户角色
     */
    private String role;

    /**
     * 用户所在校区（仅带客有）
     */
    private String campus;

    /**
     * 用户openId
     */
    private String openId;

    /**
     * 用户最近登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date recetlyLoginTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getRecetlyLoginTime() {
        return recetlyLoginTime;
    }

    public void setRecetlyLoginTime(Date recetlyLoginTime) {
        this.recetlyLoginTime = recetlyLoginTime;
    }

}
