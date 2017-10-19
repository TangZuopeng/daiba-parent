package com.daiba.dishes.service;

import com.daiba.dishes.model.Dishes;
import com.daiba.dishes.model.FirmDishes;

import java.util.List;
import java.util.Map;

/**
 * com.daiba.dishes.service
 * Dishes 的Service业务逻辑层接口
 *
 * @author TinyDolphin
 *         2017/4/7 9:32.
 */
public interface DishesService {

    /**
     * 根据商店id 查询所有菜系
     *
     * @param merchantId
     * @return
     */
    public List<Dishes> showAllDishes(String merchantId);


    /**
     * 添加多条firmDishes数据(firmId相同)
     *
     * @param firmId
     * @param firmDishesList
     */
    public void addFirmDishes(String firmId, List<FirmDishes> firmDishesList);

    /**
     * 获取购物车详情
     *
     * @param firmId
     */
    public List<Map<String, Object>> getCarts(String firmId);

    /**
     * 获取订单信息以及商家信息
     *
     * @param firmId
     */
    public List<Map<String, Object>> getMerchantMessage(String firmId);

    ///**
    // * 带饭确认送达处理
    // *
    // * @param dishesId
    // */
    //public void confirmTakeOut(String dishesId, int count);

}