package com.daiba.user.dao;

import com.daiba.mybatis.MyBatisScan;
import com.daiba.user.model.Bringer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
@MyBatisScan
public interface BringerDao {

    /**
     * 更新带客接单数
     *
     * @param id
     */
    public void updateAcceptCountAndWeekMoney(@Param("id")int id,@Param("orderMoney") int orderMoney);

    /**
     * 找出所有本周收入不为零的带客
     * @return
     */
    public List<Bringer> selectAllBringer();

    /**
     * 清除该带客一周的所得金额
     * @param bringerId
     */
    public void updateWeekIncome(@Param("bringerId")int bringerId);
}
