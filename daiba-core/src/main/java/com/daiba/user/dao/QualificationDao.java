package com.daiba.user.dao;

import com.daiba.mybatis.MyBatisScan;
import com.daiba.user.model.Address;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
@MyBatisScan
public interface QualificationDao {

    ///**
    // * 通过校区(campus) 查询认证信息Id
    // *
    // * @param campus
    // * @return
    // */
    //public List<Integer> selectQualificationIdByCampus(@Param("campus") String campus);
    //
    //
    ///**
    // * 通过认证信息id 查询所有带客Id
    // *
    // * @param idList
    // * @return
    // */
    //public List<Integer> selectBringIdById(@Param("idList") List<Integer> idList);

    /**
     * 根据校区码查询带客openId
     * @param campus1
     * @param campus2
     * @return
     */
    public List<String> selectOpenIdByCampus(@Param("campus1") String campus1,@Param("campus2") String campus2,@Param("isReceiver") int isReceiver);

    /**
     * 根据带客id获取带客的地址
     * @param briId
     * @return
     */
    public Address selectAddressByBriId(@Param("briId") int briId);



}
