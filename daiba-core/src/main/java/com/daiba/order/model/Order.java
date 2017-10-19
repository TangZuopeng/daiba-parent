package com.daiba.order.model;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
public class Order {

    private String orderId;

    /**
     * 公司(快递公司，店铺)
     */
    private String company;

    /**
     * 收件人
     */
    private String receiver;

    /**
     * 取件地址码
     */
    private String acceptAddCode;

    /**详细取件地址(不含校区、公司菜名)*/
    private String acceptAddress;

    /**
     * 取件号
     */
    private String takeNum;

    /**
     * 预留手机号码
     */
    private String reservedPhone;

    /**
     * 状态实体类(快递30、外卖31)
     */
    private int staId;

    /**
     * 详细要求
     */
    private String content;

    public Order() { }

    public Order(String company, String receiver, String acceptAddCode, String takeNum, String reservedPhone, int staId) {
        this.company = company;
        this.receiver = receiver;
        this.acceptAddCode = acceptAddCode;
        this.takeNum = takeNum;
        this.reservedPhone = reservedPhone;
        this.staId = staId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAcceptAddCode() {
        return acceptAddCode;
    }

    public void setAcceptAddCode(String acceptAddCode) {
        this.acceptAddCode = acceptAddCode;
    }

    public String getTakeNum() {
        return takeNum;
    }

    public void setTakeNum(String takeNum) {
        this.takeNum = takeNum;
    }

    public String getReservedPhone() {
        return reservedPhone;
    }

    public void setReservedPhone(String reservedPhone) {
        this.reservedPhone = reservedPhone;
    }

    public int getStaId() {
        return staId;
    }

    public void setStaId(int staId) {
        this.staId = staId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAcceptAddress() {
        return acceptAddress;
    }

    public void setAcceptAddress(String acceptAddress) {
        this.acceptAddress = acceptAddress;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", company='" + company + '\'' +
                ", receiver='" + receiver + '\'' +
                ", acceptAddCode='" + acceptAddCode + '\'' +
                ", takeNum='" + takeNum + '\'' +
                ", reservedPhone='" + reservedPhone + '\'' +
                ", staId=" + staId +
                '}';
    }

}
