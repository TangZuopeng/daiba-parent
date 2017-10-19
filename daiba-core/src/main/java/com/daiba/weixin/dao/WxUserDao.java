package com.daiba.weixin.dao;

import com.daiba.mybatis.MyBatisScan;
import com.daiba.weixin.model.WxUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 　 　　   へ　　　 　／|
 * 　　    /＼7　　　 ∠＿/
 * 　     /　│　　 ／　／
 * 　    │　Z ＿,＜　／　　   /`ヽ
 * 　    │　　　 　　ヽ　    /　　〉
 * 　     Y　　　　　   `　  /　　/
 * 　    ｲ●　､　●　　⊂⊃〈　　/
 * 　    ()　 へ　　　　|　＼〈
 * 　　    >ｰ ､_　 ィ　 │ ／／      去吧！
 * 　     / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　     ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　    7　　　　　　　|／
 * 　　    ＞―r￣￣`ｰ―＿
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * @author　 penghaitao
 * @date　 2016-09-10  16:35
 * @description　 $
 */
@MyBatisScan
public interface WxUserDao {

    public int insert(WxUser wxUser);

    public void update(WxUser wxUser);

    public List<WxUser> selectAll();

    public WxUser selectById(@Param("openid") String openid);

    public void deleteAll();

    public void deleteById(@Param("openid") String openid);

}
