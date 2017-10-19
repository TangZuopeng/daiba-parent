package com.daiba.invitation.service;

import java.util.List;

/**
 * Created by dolphinzhou on 2016/11/12.
 */
public interface InvitationService {
    /**
     * 生成邀请码
     * @param invitationCodes
     */
    public void createInvitationCode(List<String> invitationCodes);

    /**
     * 使用邀请码
     *
     * @param invitationCode
     * @return 1--邀请码未使用 2--邀请码已经被使用 3--邀请码不存在
     */
    public int useInvitationCode(String invitationCode);

}
