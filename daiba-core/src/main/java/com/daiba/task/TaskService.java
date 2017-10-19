package com.daiba.task;

import com.daiba.firm.dao.FirmDao;
import com.daiba.firm.model.Firm;
import com.daiba.order.dao.OrderDao;
import com.daiba.user.dao.BringerDao;
import com.daiba.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 定时任务（确认收货）
 * Created by tangzuopeng on 2016/11/6.
 */
public class TaskService {

    @Autowired
    private FirmDao firmDao;

    @Autowired
    private BringerDao bringerDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 定时任务：将当天23点还未确认收货的订单确认收货(状态：01--02、带客:接单数+1、本周收入增加)
     */
    public void confirmAcceptFirmTask() {
        int oldOrderState = 01;//已接单
        int newOrderState = 02;//已完成
        Date filishTime = new Date();
        List<Firm> firmList = firmDao.selectAllFirmByOrderState(oldOrderState);
        for(Firm firm : firmList){
            bringerDao.updateAcceptCountAndWeekMoney(firm.getBriId(),firm.getOrderMoney());
        }
        firmDao.updateAllFirmByOrderState(oldOrderState,newOrderState,filishTime);
    }

    /**
     * 定时任务：凌晨三点，清理所有未付款订单
     */
    public void clearNotPayFirm() {
        int orderState = 04;//未付款
        List<Firm> firms = firmDao.selectAllFirmByOrderState(orderState);
        firmDao.deleteNoPayFirm(orderState);
        String orderId = null;
        for(Firm firm:firms){
            orderId = firm.getFirmId();
            orderDao.deleteOrder(orderId);
        }
    }

//    @Override
//    public void payBringerTask() {
//        List<Bringer> list = bringerDao.selectAllBringer();
//        String openId = null;
//        int weekIncome = 0;
//        for(Bringer bringer : list){
//            int bringerId = bringer.getBringerId();
//            openId = userDao.selectOpenIdByBringId(bringerId);
//            weekIncome = bringer.getWeekIncome();
//            if(true){
//                bringerDao.updateWeekIncome(bringerId);
//            }
//
//        }
//    }

//    /**
//     * 定时任务，每周结算带客酬劳
//     */
//    public void payBringerTask();


}
