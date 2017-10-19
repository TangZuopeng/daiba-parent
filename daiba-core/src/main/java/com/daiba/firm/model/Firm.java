package com.daiba.firm.model;

import com.daiba.order.model.Order;
import com.daiba.user.model.User;

import java.util.Date;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
public class Firm {
    /**
     * 业务流水id(日期+四位序号)
     */
    private String firmId;

    /**
     * 发单时间
     */
    private Date giveTime;

    /**
     * 接单时间
     */
    private Date acceptTime;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 退单时间
     */
    private Date cancleTime;

    /**
     * 送达地址
     */
    private String address;

    /**
     * 要求送达时间( 下单之后一个小时之外的时间 ) 比如：下单时间为3:00 要求送达时间：只能选择4:00之后的时间。
     */
    private Date askTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单金额
     */
    private int orderMoney;

    /**
     * 带客ID
     */
    private int briId;

    /**
     * 用户实体类
     */
    private User user;

    /**
     * 订单ID
     */
    private Order order;

    /**
     * 订单状态类(未接单00、已接单01、已完成02、已取消03)
     */
    private int orderState;

    /**
     * 是否申请取消订单(0：为申请、1：已申请)
     */
    private int isApplyCancel;

    /**
     * 物品本金
     */
    private int goodsMoney;

    public int getGoodsMoney() {
        return goodsMoney;
    }

    public void setGoodsMoney(int goodsMoney) {
        this.goodsMoney = goodsMoney;
    }

    public String getFirmId() {
        return firmId;
    }

    public void setFirmId(String firmId) {
        this.firmId = firmId;
    }

    public Date getGiveTime() {
        return giveTime;
    }

    public void setGiveTime(Date giveTime) {
        this.giveTime = giveTime;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getCancleTime() {
        return cancleTime;
    }

    public void setCancleTime(Date cancleTime) {
        this.cancleTime = cancleTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getAskTime() {
        return askTime;
    }

    public void setAskTime(Date askTime) {
        this.askTime = askTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(int orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getBriId() {
        return briId;
    }

    public void setBriId(int briId) {
        this.briId = briId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }

    public int getIsApplyCancel() {
        return isApplyCancel;
    }

    public void setIsApplyCancel(int isApplyCancel) {
        this.isApplyCancel = isApplyCancel;
    }

    public Firm() {
    }

    public Firm(Date giveTime, Date acceptTime, Date finishTime, Date cancleTime, String address, Date askTime, String remark, int orderMoney, int briId, User user, Order order, int orderState, int isApplyCancel) {
        this.giveTime = giveTime;
        this.acceptTime = acceptTime;
        this.finishTime = finishTime;
        this.cancleTime = cancleTime;
        this.address = address;
        this.askTime = askTime;
        this.remark = remark;
        this.orderMoney = orderMoney;
        this.briId = briId;
        this.user = user;
        this.order = order;
        this.orderState = orderState;
        this.isApplyCancel = isApplyCancel;
    }

    @Override
    public String toString() {
        return "Firm{" +
                "firmId='" + firmId + '\'' +
                ", giveTime=" + giveTime +
                ", acceptTime=" + acceptTime +
                ", finishTime=" + finishTime +
                ", cancleTime=" + cancleTime +
                ", address='" + address + '\'' +
                ", askTime=" + askTime +
                ", remark='" + remark + '\'' +
                ", orderMoney=" + orderMoney +
                ", briId=" + briId +
                ", user=" + user +
                ", order=" + order +
                ", orderState=" + orderState +
                ", isApplyCancel=" + isApplyCancel +
                '}';
    }
}
