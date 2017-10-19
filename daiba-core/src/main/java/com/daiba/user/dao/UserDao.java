package com.daiba.user.dao;

import com.daiba.mybatis.MyBatisScan;
import com.daiba.user.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by dolphinzhou on 2016/9/27.
 */
@MyBatisScan
public interface UserDao {
    /**
     * 插入用户
     *
     * @param user
     */

    public void insert(User user);

    /**
     * 更新用户
     *
     * @param user
     */
    public void update(User user);

    /**
     * 根据用户ID，获取用户实例
     *
     * @param id
     * @return
     */
    public User selectById(int id);

    /**
     * 根据用户手机号码，获取用户实例
     *
     * @param phoneNum
     * @return
     */
    public User selectByPhoneNum(String phoneNum);

    /**
     * 根据手机号号码，获取用户ID
     *
     * @param phoneNum
     * @return
     */
    public String selectIdByPhoneNum(String phoneNum);

    /**
     * 更新用户状态(最近登录时间)
     *
     * @param user
     */
    public void updateUserInfo(User user);

    /**
     * 根据发单人ID 查询用户简要信息
     */
    public User selectSendUserInfo(int id);

    /**
     * 根据带客ID 查询用户简要信息
     */
    public User selectAcceptUserInfo(int briId);

    /**
     * 增加用户的发单数，总支出
     *
     * @param userId
     * @param spending
     */
    public void updateUserOrderNumAndSpending(@Param("userId") int userId, @Param("spending") int spending);

    /**
     * 根据带客id查询用户openId
     *
     * @param briId
     * @return
     */
    public String selectOpenIdByBringId(@Param("briId") int briId);

    /**
     * 减少用户的发单数，总支出
     *
     * @param userId
     * @param spending
     */
    public void downUserOrderNumAndSpending(@Param("userId") int userId, @Param("spending") int spending);

    /**
     * 根据用户openId查询到用户手机号码
     *
     * @return
     */
    public User selectUserByOpenId(@Param("openId") String openId);

    /**
     * 查询出所有用户的openId
     *
     * @return
     */
    public List<String> selectAllOpenId(@Param("role") String role);

    /**
     * 查询出所有用户信息
     *
     * @return
     */
    public List<User> selectAllUserInfo();

    public User selectUserByBriId(@Param("briId") int briId);
}
