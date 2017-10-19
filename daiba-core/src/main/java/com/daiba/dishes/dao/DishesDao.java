package com.daiba.dishes.dao;

import com.daiba.dishes.model.Dishes;
import com.daiba.dishes.model.FirmDishes;
import com.daiba.mybatis.MyBatisScan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * com.daiba.dishes.dao
 *
 * @author TinyDolphin
 *         2017/4/7 9:34.
 */
@MyBatisScan
public interface DishesDao {

    /**
     * 根据所有的商户ID 查询出相对应的所有菜品
     *
     * @param merchantId
     * @return
     */
    public List<Dishes> selectAllDishesByMerchantId(@Param("merchantId") String merchantId);


    /**
     * 插入多行数据到 firm_dishes表
     *
     * @param firmId
     * @param firmDishesList
     */
    public void insertFirmDishes(@Param("list") List<FirmDishes> firmDishesList, @Param("firmId") String firmId);

    /**
     * 查询订单页面购物列表
     *
     * @param firmId
     */
    public List<Map<String, Object>> selectCartByFirmId(String firmId);

    /**
     * 查询订单信息以及商家信息
     *
     * @param firmId
     */
    public List<Map<String, Object>> selectMerchantMessageByFirmId(String firmId);

    /**
     * 通过菜品码，更新菜品的月销售量
     *
     * @param dishesId
     */
    public void updateDishesVolumeById(@Param("dishesId") String dishesId, @Param("count") int count);
}
