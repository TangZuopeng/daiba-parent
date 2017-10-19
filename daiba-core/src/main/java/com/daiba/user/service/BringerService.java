package com.daiba.user.service;

import java.util.List;

/**
 * Created by dolphinzhou on 2016/10/16.
 */
public interface BringerService {
    /**
     * 通过校区获取带客的openId
     *
     * @param campus1
     * @param campus2
     * @return
     */
    public List<String> getOpenIdByCampus(String campus1,String campus2,int isReceiver);
}
