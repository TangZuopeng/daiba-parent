package com.daiba.merchant.dao;

import com.daiba.merchant.model.Merchant;
import com.daiba.mybatis.MyBatisScan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * com.daiba.merchant.dao
 *
 * @author TinyDolphin
 *         2017/4/7 9:34.
 */
@MyBatisScan
public interface MerchantDao {

    /**
     * 根据商户ID前7位(学校+校区+地址类别)查询所有店铺
     *
     * @param code
     * @return
     */
    public List<Merchant> selectAllMerchantByCode(@Param("code") String code,@Param("state") int state);

    /**
     * 更新商家本周收入以及本周销量
     * @param merchantId
     * @param goodsMoney
     */
    public void updateMerchantIncomeAndSaleById(@Param("merchantId") String merchantId,@Param("goodsMoney") int goodsMoney,@Param("count") int count);
}
