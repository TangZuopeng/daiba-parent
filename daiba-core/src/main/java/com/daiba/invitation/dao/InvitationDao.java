package com.daiba.invitation.dao;

import com.daiba.mybatis.MyBatisScan;
import org.apache.ibatis.annotations.Param;

/**
 * Created by dolphinzhou on 2016/11/12.
 */
@MyBatisScan
public interface InvitationDao {

    public void insert(@Param("invitationCode") String invitationCode);

    public void update(@Param("invitationCode") String invitationCode,@Param("codeStatue") String codeStatue);

    public String select(@Param("invitationCode") String invitationCode);
}
