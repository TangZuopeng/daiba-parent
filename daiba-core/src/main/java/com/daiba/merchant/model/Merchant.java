package com.daiba.merchant.model;

import com.daiba.dishes.model.Dishes;

import java.util.List;

/**
 * com.daiba.merchant.model
 *
 * @author TinyDolphin
 *         2017/4/7 9:35.
 */
public class Merchant {

    /**
     * 商户id(学校4位+校区2位+地址类别1位+地址3位)
     */
    private String merchantId;

    /**
     * 商户名
     */
    private String merchantName;

    /**
     * 商户类型
     */
    private int type;

    /**
     * 商户状态
     */
    private int merchantState;

    /**
     * 商户地址
     */
    private String merchantAdd;

    /**
     * 商户联系方式
     */
    private String merchantPhone;

    /**
     * 商户openid
     */
    private String openId;

    /**
     * 商户周收入
     */
    private int weekIncome;

    /**
     * 总收入
     */
    private int income;

    /**
     * 周销售
     */
    private int weekSale;

    /**
     * 总销售
     */
    private int sale;

    /**
     * 1：自营 2：非自营
     */
    private int isSelf;

    /**
     * 菜品实体类集合
     */
    private List<Dishes> dishesList;

    public Merchant() {
        super();
    }

    public Merchant(String merchantId, String merchantName, int type, int merchantState, String merchantAdd, String merchantPhone, String openId, int weekIncome, int income, int weekSale, int sale, int isSelf, List<Dishes> dishesList) {
        this.merchantId = merchantId;
        this.merchantName = merchantName;
        this.type = type;
        this.merchantState = merchantState;
        this.merchantAdd = merchantAdd;
        this.merchantPhone = merchantPhone;
        this.openId = openId;
        this.weekIncome = weekIncome;
        this.income = income;
        this.weekSale = weekSale;
        this.sale = sale;
        this.isSelf = isSelf;
        this.dishesList = dishesList;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMerchantState() {
        return merchantState;
    }

    public void setMerchantState(int merchantState) {
        this.merchantState = merchantState;
    }

    public String getMerchantAdd() {
        return merchantAdd;
    }

    public void setMerchantAdd(String merchantAdd) {
        this.merchantAdd = merchantAdd;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getWeekIncome() {
        return weekIncome;
    }

    public void setWeekIncome(int weekIncome) {
        this.weekIncome = weekIncome;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public List<Dishes> getDishesList() {
        return dishesList;
    }

    public int getWeekSale() {
        return weekSale;
    }

    public void setWeekSale(int weekSale) {
        this.weekSale = weekSale;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
    }

    public void setDishesList(List<Dishes> dishesList) {
        this.dishesList = dishesList;
    }

    public int getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(int isSelf) {
        this.isSelf = isSelf;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "merchantId='" + merchantId + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", type=" + type +
                ", merchantState=" + merchantState +
                ", merchantAdd='" + merchantAdd + '\'' +
                ", merchantPhone='" + merchantPhone + '\'' +
                ", openId='" + openId + '\'' +
                ", weekIncome=" + weekIncome +
                ", income=" + income +
                ", weekSale=" + weekSale +
                ", sale=" + sale +
                ", isSelf=" + isSelf +
                ", dishesList=" + dishesList +
                '}';
    }
}
