package com.daiba.dishes.service.impl;

import com.daiba.dishes.dao.DishesDao;
import com.daiba.dishes.model.Dishes;
import com.daiba.dishes.model.FirmDishes;
import com.daiba.dishes.service.DishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * com.daiba.dishes.service.impl
 *
 * @author TinyDolphin
 *         2017/4/7 9:34.
 */
@Service("dishesService")
public class DishesServiceImpl implements DishesService {

    @Autowired
    private DishesDao dishesDao;

    @Override
    public List<Dishes> showAllDishes(String merchantId) {
        return dishesDao.selectAllDishesByMerchantId(merchantId);
    }

    @Override
    public void addFirmDishes(String firmId, List<FirmDishes> firmDishesList) {
        dishesDao.insertFirmDishes(firmDishesList, firmId);
    }

    @Override
    public List<Map<String, Object>> getCarts(String firmId) {
        return dishesDao.selectCartByFirmId(firmId);
    }

    @Override
    public List<Map<String, Object>> getMerchantMessage(String firmId) {
        return dishesDao.selectMerchantMessageByFirmId(firmId);
    }

}
