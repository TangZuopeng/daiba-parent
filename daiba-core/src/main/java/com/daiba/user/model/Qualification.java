package com.daiba.user.model;

import java.util.Date;

/**
 * @author qiuyong
 * @date 2016/10/6.
 * @declare  用户认证流水表
 */
public class Qualification{
    private int qualificationId;
    /**
     * 用户id号
     */
    private int useId;
    /**
     * 带客id号
     */
    private int briId;
    /**
     * 管理员id号
     */
    private int adm_id;
    /**
     * 认证_学生号
     */
    private String studentNum;
    /**
     *认证_身份证号
     */
    private String humanId;

    /**
     * 申请时间
     */
    private Date applyTime;
    /**
     * 认证_状态描述id号
     */
    private int staId;
    /**
     * 认证_学生证正面图片的认证地址
     */
    private String studentCardFront;
    /**
     * 认证_学生证背面图片的认证地址
     */
    private String studentCardReverse;
    /**
     *  身份证正面图片的认证地址
     */
    private String idCardFront;
    /**
     * 学生证反面图片的认证地址
     */
    private String idCardReverse;
    /**
     * 认证时间
     */
    private Date checkTime;

    /**
     * 真实姓名
     */
    private String realName;

    public Qualification() {
    }

    public int getQualificationId() {
        return qualificationId;
    }

    public void setQualificationId(int qualificationId) {
        this.qualificationId = qualificationId;
    }

    public int getUseId() {
        return useId;
    }

    public void setUseId(int useId) {
        this.useId = useId;
    }

    public int getBriId() {
        return briId;
    }

    public void setBriId(int briId) {
        this.briId = briId;
    }

    public int getAdm_id() {
        return adm_id;
    }

    public void setAdm_id(int adm_id) {
        this.adm_id = adm_id;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getHumanId() {
        return humanId;
    }

    public void setHumanId(String humanId) {
        this.humanId = humanId;
    }

    public int getStaId() {
        return staId;
    }

    public void setStaId(int staId) {
        this.staId = staId;
    }

    public String getStudentCardFront() {
        return studentCardFront;
    }

    public void setStudentCardFront(String studentCardFront) {
        this.studentCardFront = studentCardFront;
    }

    public String getStudentCardReverse() {
        return studentCardReverse;
    }

    public void setStudentCardReverse(String studentCardReverse) {
        this.studentCardReverse = studentCardReverse;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardReverse() {
        return idCardReverse;
    }

    public void setIdCardReverse(String idCardReverse) {
        this.idCardReverse = idCardReverse;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
