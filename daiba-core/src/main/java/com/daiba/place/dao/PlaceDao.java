package com.daiba.place.dao;

import com.daiba.mybatis.MyBatisScan;
import com.daiba.place.model.Place;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by dolphinzhou on 2016/10/23.
 */
@MyBatisScan
public interface PlaceDao {

    /**
     * 根据 code 查询快递详细地址
     *
     * @param companyCode 快递公司码
     * @param addCode   快递地点码
     * @param campusCode    快递校区码
     * @return
     */
    public List<String> select3NameByCode(@Param("companyCode") String companyCode ,@Param("addCode") String addCode ,@Param("campusCode") String campusCode);

    //根据parentId获得字节的地址信息
    public List<Place> getPlacesByParentId(String parentId);
}
