package com.daiba.user.model;

/**
 * Created by qiuyong on 2016/9/28.
 */
public class ConsigneeMessage {
    /**
     * 快递个人中心_地址主键
     */
    private int conmsgId;
    /**
     * 快递个人中心_用户id号
     */
    private  int useId;
    /**
     * 快递个人中心_称呼
     */
    private String callName;
    /**
     * 快递个人中心_手机号码
     */
    private String phoneNum;
    /**
     * 快递个人中心_学校
     */
    private String school;
    /**
     * 快递个人中心_校区
     */
    private String campus;
    /**
     * 快递个人中心_楼栋
     */
    private String build;
    /**
     * 快递个人中心_房间号
     */
    private String room;

    public ConsigneeMessage() { }

    public ConsigneeMessage(int useId, String call, String phoneNum, String campus, String build, String room) {
        this.useId = useId;
        this.callName = call;
        this.phoneNum = phoneNum;
        this.campus = campus;
        this.build = build;
        this.room = room;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public int getConmsgId() {
        return conmsgId;
    }

    public void setConmsgId(int conmsgId) {
        this.conmsgId = conmsgId;
    }

    public int getUseId() {
        return useId;
    }

    public void setUseId(int useId) {
        this.useId = useId;
    }

    public String getCallName() {
        return callName;
    }

    public void setCall(String callName) {
        this.callName = callName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
