package com.daiba.user.model;

import com.daiba.firm.model.Firm;

import java.util.List;

/**
 * 带客信息表
 * Created by dolphinzhou on 2016/10/16.
 */
public class Bringer {
    private int bringerId;
    /**
     * 接单数
     */
    private int acceptCount;
    /**
     * 信誉程度
     */
    private double creditworthiness;
    /**
     * 差评数
     */
    private int badRepCount;
    /**
     * 中评数
     */
    private int mediumRepCount;

    /**
     * 好评数
     */
    private int goodRepCount;
    /**
     * 收入
     */
    private int income;

    /**
     * 本周收入
     */
    private int weekIncome;

    /**
     * 订单流水线(维护 bringer 表和 firm 表之间的一对多关系)
     */
    private List<Firm> firms;

    public int getBringerId() {
        return bringerId;
    }

    public void setBringerId(int bringerId) {
        this.bringerId = bringerId;
    }

    public int getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(int acceptCount) {
        this.acceptCount = acceptCount;
    }

    public double getCreditworthiness() {
        return creditworthiness;
    }

    public void setCreditworthiness(double creditworthiness) {
        this.creditworthiness = creditworthiness;
    }

    public int getBadRepCount() {
        return badRepCount;
    }

    public void setBadRepCount(int badRepCount) {
        this.badRepCount = badRepCount;
    }

    public int getMediumRepCount() {
        return mediumRepCount;
    }

    public void setMediumRepCount(int mediumRepCount) {
        this.mediumRepCount = mediumRepCount;
    }

    public int getGoodRepCount() {
        return goodRepCount;
    }

    public void setGoodRepCount(int goodRepCount) {
        this.goodRepCount = goodRepCount;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getWeekIncome() {
        return weekIncome;
    }

    public void setWeekIncome(int weekIncome) {
        this.weekIncome = weekIncome;
    }

    public List<Firm> getFirms() {
        return firms;
    }

    public void setFirms(List<Firm> firms) {
        this.firms = firms;
    }

    public Bringer() {
    }

    public Bringer(int acceptCount, double creditworthiness, int badRepCount, int mediumRepCount, int goodRepCount, int income, int weekIncome, List<Firm> firms) {
        this.acceptCount = acceptCount;
        this.creditworthiness = creditworthiness;
        this.badRepCount = badRepCount;
        this.mediumRepCount = mediumRepCount;
        this.goodRepCount = goodRepCount;
        this.income = income;
        this.weekIncome = weekIncome;
        this.firms = firms;
    }

    @Override
    public String toString() {
        return "Bringer{" +
                "bringerId=" + bringerId +
                ", acceptCount=" + acceptCount +
                ", creditworthiness=" + creditworthiness +
                ", badRepCount=" + badRepCount +
                ", mediumRepCount=" + mediumRepCount +
                ", goodRepCount=" + goodRepCount +
                ", income=" + income +
                ", weekIncome=" + weekIncome +
                ", firms=" + firms +
                '}';
    }
}
