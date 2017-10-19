package com.daiba.firm.service;

import com.daiba.firm.model.Firm;
import com.daiba.user.model.User;

import java.util.List;
import java.util.Map;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
public interface FirmService {
    /**
     * 插入一个业务流水表
     *
     * @param firm
     * @param userId  用户id号
     * @param orderId
     */
    public void addFirm(Firm firm, int userId, String orderId);


    /**
     * 通过显示所有的订单:未接单、已接单、已完成(传入的地址码等于Order表的acceptAddCode)
     *
     * @param acceptAddCode : 100121514
     * @return 订单流水线的实体类集合
     */
    /**
     * 通过显示所有的订单:未接单、已接单、已完成(传入的地址码等于Order表的acceptAddCode)
     *
     * @param acceptAddCode acceptAddCode : 100121514
     * @param orderType 订单类型
     * @param count         首页订单数量
     * @return 订单流水线的实体类集合
     */
    public List<Firm> showAllFirm(String acceptAddCode, int orderType, int count);

    /**
     * 通过 useId、订单流水线状态 获取该用户所有firm订单流水线
     *
     * @param useId
     * @param orderState
     * @return
     */
    public List<Firm> showUserAllFirm(int useId, int orderState);

    /**
     * 通过 briId 、订单流水线状态 获取该用户所有firm订单流水线
     *
     * @param briId
     * @param orderState
     * @return
     */
    public List<Firm> showBringerAllFirm(int briId, int orderState);

    /**
     * 接单绑定带客操作( firm 表里面绑定 bringer 的 id ), 并更新 firm 的 order_state 订单状态( 已接单 )
     *
     * @param firmId
     * @param briId
     */
    public Firm acceptFirm(String firmId, int briId);

    /**
     * 根据firmId 获取 Firm 详情
     *
     * @param firmId
     * @return
     */
    public Firm getFirmDetail(String firmId);

    /**
     * 取消该订单流水线(更改该订单流水线的状态为：已取消---03,更新订单流水线取消时间、带客带单数-1、金额减少)
     * 1--取消订单成功  2--取消订单失败(该订单状态不为未接单)  3--取消订单失败(该订单不存在，可能被删除) 4--取消订单失败失败(该用户信息与该订单信息不匹配)
     */
    public int cancelFirm(String firmId, String phoneNum);

    ///**
    // * 用户确认收货(更改该订单流水线的状态为：已完成---02、更新订单流水线完成时间、带客带单数+1、金额增加)
    // *
    // * @param firmId
    // * @return 1--确认收货成功  2--确认收货失败(该订单状态不为已接单)  3--确认收获失败(该订单不存在，可能被删除) 4--确认收获失败(该用户信息与该订单信息不匹配)
    // */
    //public int confirmFirm(String firmId, String phoneNum, String OpenId);

    /**
     * 带客确认送达(更改该订单流水线的状态为：已完成---02、更新订单流水线完成时间、带客带单数+1、金额增加)
     *
     * @param firmId
     * @return 1--确认送达成功  2--确认送达败(该订单状态不为已接单)  3--确认送达失败(该订单不存在，可能被删除) 4--确认送达失败(该用户信息与该订单信息不匹配)
     */
    public int confirmFirm(List<Map<String,Object>> firmMerchantMessages, String firmId, int briId, String OpenId);

    /**
     * 支付完成(更改该订单流水线的状态为：未接单---00)
     *
     * @param firmId
     */
    public void payFinishFirm(String firmId);

    /**
     * 根据订单流水号ID 获取订单流水号状态
     *
     * @param firmId
     * @return
     */
    public int getFirmStatus(String firmId);

    /**
     * 申请取消订单
     *
     * @param firmId
     * @param user
     * @return 1-申请成功 2-申请失败(该订单已经申请) 3-申请失败(该订单状态不为已接单) 4-申请失败(该订单不存在) 5-申请失败(该用户信息与订单信息不匹配)
     */
    public int applyCancelFirm(String firmId, User user);

    /**
     * 带客取消该订单流水线(更改该订单流水线的状态为：已取消---03,更新订单流水线取消时间、带客带单数-1、金额减少)
     * 1--取消订单成功  2--取消订单失败(该订单状态不为已接单)  3--取消订单失败(该订单不存在，可能被删除) 4--取消订单失败失败(该带客信息与该订单信息不匹配)
     */
    public int cancelFirmByBringer(String firmId , String phoneNum);

    /**
     * 在管理员端订单面板上显示所有的订单
     * @param dataTableParams 获取表格的参数 start
     * @return
     */

    /**
     * 在管理员端订单面板上显示所有的订单
     * @return
     */
    public List<Firm> loadFirmsToAdmin()throws Exception;

    public Firm loadFirmDetailToAdmin(String firmId)throws Exception;

}
