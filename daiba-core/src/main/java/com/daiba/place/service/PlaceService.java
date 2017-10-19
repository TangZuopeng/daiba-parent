package com.daiba.place.service;

import java.util.Map;

/**
 * Created by dolphinzhou on 2016/10/23.
 */
public interface PlaceService {

    /**
     * 根据订单的地址码查询place里面的订单公司地址码、所在地地址码、校区地址码
     * @param code
     * @return
     */
    public String getPlace3NameById(String code);

    public Map<String,Object> showLinkedList(int userId) throws Exception;
}
