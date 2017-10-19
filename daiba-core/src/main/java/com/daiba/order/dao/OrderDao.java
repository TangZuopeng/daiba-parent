package com.daiba.order.dao;

import com.daiba.mybatis.MyBatisScan;
import com.daiba.order.model.Order;
import org.apache.ibatis.annotations.Param;

/**
 * 　 　　   へ　　　 　／|
 * 　　    /＼7　　　 ∠＿/
 * 　     /　│　　 ／　／
 * 　    │　Z ＿,＜　／　　   /`ヽ
 * 　    │　　　 　　ヽ　    /　　〉
 * 　     Y　　　　　   `　  /　　/
 * 　    ｲ●　､　●　　⊂⊃〈　　/
 * 　    ()　 へ　　　　|　＼〈
 * 　　    >ｰ ､_　 ィ　 │ ／／      去吧！
 * 　     / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　     ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　    7　　　　　　　|／
 * 　　    ＞―r￣￣`ｰ―＿
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * @author　 penghaitao
 * @date　 2016-10-01  18:48
 * @description　 $
 */
@MyBatisScan
public interface OrderDao {

    /**
     * 根据 id 获取 order
     *
     * @param id
     * @return
     */
    public Order selectOrderById(String id);

    //插入一个订单
    public void addOrder(Order order);

    //根据取件号获得一个订单
    public Order getOrderByToakenNum(String tokenNum);

    /**
     * 根据 orderId 删除订单
     */
    public void deleteOrder(@Param("orderId") String orderId);

}
