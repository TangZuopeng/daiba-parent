package com.daiba.firm.dao;

import com.daiba.firm.model.Firm;
import com.daiba.mybatis.MyBatisScan;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
@MyBatisScan
public interface FirmDao {

    /**
     * 根据 Order 表的 acceptAddCode(校区地址码) 查询所有的流水线（获取首页订单流水线firm简要信息）
     *
     * @param acceptAddCode
     * @param staId
     * @return
     */
    public List<Firm> selectAllFirmByOrderAcceptAddCode(@Param("acceptAddCode") String acceptAddCode, @Param("staId") int staId, @Param("count") int count);

    /**
     * 根据 firm 表的 orderState(订单流水线的状态) 查询所有的流水线
     *
     * @param orderState
     * @return
     */
    public List<Firm> selectAllFirmByOrderState(@Param("orderState") int orderState);

    /**
     * 根据用户 ID 、流水线状态 获取用户订单页面流水线firm简要信息
     *
     * @param useId
     * @param orderState
     * @return
     */
    public List<Firm> selectAllFirmByUserId(@Param("useId") int useId, @Param("orderState") int orderState);

    /**
     * 根据带客 ID 流水线状态 获取用户订单页面流水线firm简要信息
     *
     * @param briId
     * @param orderState
     * @return
     */
    public List<Firm> selectAllFirmByBringerId(@Param("briId") int briId, @Param("orderState") int orderState);

    /**
     * 加入订单流水线带客信息
     *
     * @param firmId
     * @param briId
     */
    public void updateFirmBringer(@Param("firmId") String firmId, @Param("briId") int briId);

    /**
     * 接单之后更新接单时间、订单状态(已接单)
     *
     * @param firmId
     * @param orderState
     * @param acceptTime
     */
    public void updateFirmAcceptTime(@Param("firmId") String firmId, @Param("orderState") int orderState, @Param("acceptTime") Date acceptTime);

    /**
     * 接单之后更新接单时间、订单状态(已接单)
     *
     * @param firmId
     * @param orderState
     * @param cancleTime
     */
    public void updateFirmCancleTime(@Param("firmId") String firmId, @Param("orderState") int orderState, @Param("cancleTime") Date cancleTime);

    /**
     * 接单之后更新接单时间、订单状态(已接单)
     *
     * @param firmId
     * @param orderState
     * @param finishTime
     */
    public void updateFirmFinishTime(@Param("firmId") String firmId, @Param("orderState") int orderState, @Param("finishTime") Date finishTime);

    /**
     * 支付之后更新订单状态(未付款->未接单)
     *
     * @param firmId
     * @param orderState
     */
    public void updataFirmPayFinish(@Param("firmId") String firmId, @Param("orderState") int orderState);


    /**
     * 根据订单流水号ID 查询订单流水的状态
     *
     * @param firmId
     */
    public int selectFirmSatus(@Param("firmId") String firmId);

    /**
     * 获取订单详情(订单流水线firm详细信息)
     *
     * @param firmId
     * @return
     */
    public Firm selectFirmDetail(String firmId);

    public void addFirm(@Param("firm") Firm firm, @Param("userId") int userId, @Param("orderId") String orderId);

    /**
     * 更新订单流水线状态(确认收货：统一在23点，将所有orderState==01的订单设置为 02 已完成)
     *
     * @param oldOrderState 01
     * @param newOrderState 02
     */
    public void updateAllFirmByOrderState(@Param("oldOrderState") int oldOrderState, @Param("newOrderState") int newOrderState, @Param("finishTime") Date finishTime);

    /**
     * 删除所有为付款的订单
     *
     * @param orderState 04
     */
    public void deleteNoPayFirm(@Param("orderState") int orderState);

    ///**
    // * 根据firmId查询出firm的简要信息
    // *
    // * @return
    // */
    //public Firm selectPhoneNumAndMoneyByFirmId(@Param("firmId") String firmId);

    public void updateFirm(@Param("firmId") String firmId, @Param("orderState") int orderState);

    /**
     * 根据firmId查询订单流水线是否申请取消
     *
     * @param firmId
     * @return
     */
    public int selectIsApplyCancel(@Param("firmId") String firmId);

    /**
     * 申请取消订单
     *
     * @param firmId
     * @param isApplyCancel
     */
    public void updateIsApplyCancel(@Param("firmId") String firmId, @Param("isApplyCancel") int isApplyCancel);

    /**
     * 管理端获取订单列表
     * @return
     */
    public List<Firm>  selectFirmsByTableParams();

    /**
     * 管理端获取订单详情
     * @param firmId
     * @return
     */
    public Firm selectFirmDetailToAdmin(String firmId);

}
