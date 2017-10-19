package com.daiba.moneyCard.model;

import java.util.Date;

/**
 * 带吧代金券
 * Created by Administrator on 2017/2/27.
 */
public class MoneyCard {


    /**
     * 代金券Id
     */
    private int cardId;

    /**
     * 用户Id
     */
    private int userId;

    /**
     * 卡券码
     */
    private String cardCode;

    /**
     * 有效起始日期
     */
    private Date startDate;

    /**
     * 有效结束日期
     */
    private Date finishDate;

    /**
     * 满多少钱减
     */
    private int startMoney;

    /**
     * 减免金额
     */
    private int money;

    /**
     * 面对类型：快递（30）, 食堂（31）, 其他（32）
     */
    private int type;

    /**
     * 是否有效
     */
    private int isHave;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public int getStartMoney() {
        return startMoney;
    }

    public void setStartMoney(int startMoney) {
        this.startMoney = startMoney;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsHave() {
        return isHave;
    }

    public void setIsHave(int isHave) {
        this.isHave = isHave;
    }

    public MoneyCard() {
    }

    public MoneyCard(int cardId, int userId, String cardCode, Date startDate, Date finishDate, int startMoney, int money, int type, int isHave) {
        this.cardId = cardId;
        this.userId = userId;
        this.cardCode = cardCode;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.startMoney = startMoney;
        this.money = money;
        this.type = type;
        this.isHave = isHave;
    }
}
