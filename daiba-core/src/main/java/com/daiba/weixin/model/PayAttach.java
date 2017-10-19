package com.daiba.weixin.model;

import com.daiba.firm.model.Firm;
import com.daiba.order.model.Order;

/**
 * Created by tangzuopeng on 2016/11/8.
 */
public class PayAttach {

    private int userId;

    private Firm firm;

    private Order order;

    public PayAttach() {
    }

    public Firm getFirm() {
        return firm;
    }

    public void setFirm(Firm firm) {
        this.firm = firm;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getUserId() {

        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
