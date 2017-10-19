package com.daiba.message.service;

/**
 * Created by tangzuopeng on 2017/3/16.
 */

import java.util.Map;

/**
 * 微信信息回复接口
 */
public interface MessageService {

    //回复消息
    public String getMessage(Map<Object, Object> map);


}
