package com.daiba.status.model;

import com.daiba.firm.model.Firm;
import com.daiba.order.model.Order;

import java.util.List;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
public class Status {
    private int statusId;
    /**
     * 状态字段
     */
    private String fields;
    /**
     * 状态描述
     */
    private String description;

    /**
     * 使用一个List<Order>集合属性表示该状态的流水线
     * 维护 status 表和 order 表之间的一对多关系
     */
    private List<Order> orders;

    /**
     * 使用一个List<Firm>集合属性表示该状态的订单
     * 维护 status 表和 firm 表之间的一对多关系
     */
    private List<Firm> firms;

    public List<Firm> getFirms() {
        return firms;
    }

    public void setFirms(List<Firm> firms) {
        this.firms = firms;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status() {}

    public Status(String fields, String description) {
        super();
        this.fields = fields;
        this.description = description;
    }

    @Override
    public String toString() {
        return "StatusDao{" +
                "statusId=" + statusId +
                ", fields='" + fields + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
