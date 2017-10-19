package com.daiba.merchant.service;

import com.daiba.merchant.model.Merchant;

import java.util.List;

/**
 * com.daiba.merchant.service
 * Merchant 的Service业务逻辑层接口
 *
 * @author TinyDolphin
 *         2017/4/7 9:33.
 */
public interface MerchantService {

    /**
     * 根据校区、类型查询所有商铺
     *
     * @param schoolCode
     * @param campusCode
     * @param addTypeCode
     * @return
     */
    public List<Merchant> showAllMerchant(String schoolCode, String campusCode, String addTypeCode);

    ///**
    // * 带饭确认送达处理
    // * @param merchantId
    // * @param goodsMoney
    // * @param count
    // */
    //public void confirmTakeOut(String merchantId, double goodsMoney, int count);
}
