package com.daiba.userInfo.dao;

import com.daiba.mybatis.MyBatisScan;
import com.daiba.userInfo.model.UserInfo;

import java.util.List;

/**
 * Created by Tangzuopeng on 2016/12/17.
 */
@MyBatisScan
public interface UserInfoDao {

    public List<UserInfo> selectAllUserInfo();
}
