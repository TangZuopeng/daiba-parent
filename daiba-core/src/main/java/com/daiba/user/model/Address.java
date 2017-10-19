package com.daiba.user.model;

/**
 * Created by dolphinzhou on 2016/12/15.
 */

/**
 * 带客认证信息(地址)表
 */
public class Address {
    int addressId;
    /**
     * 认证信息ID
     */
    String qualificationId;
    /**
     * 学校(默认：长春工业大学)
     */
    String school;
    /**
     * 校区
     */
    String campus;
    /**
     * 楼栋
     */
    String build;
    /**
     * 房间号
     */
    String room;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId;
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

    public Address() {
    }

    public Address(String qualificationId, String school, String campus, String build, String room) {
        super();
        this.qualificationId = qualificationId;
        this.school = school;
        this.campus = campus;
        this.build = build;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", qualificationId='" + qualificationId + '\'' +
                ", school='" + school + '\'' +
                ", campus='" + campus + '\'' +
                ", build='" + build + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
