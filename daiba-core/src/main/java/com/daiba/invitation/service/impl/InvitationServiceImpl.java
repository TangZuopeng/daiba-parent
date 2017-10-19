package com.daiba.invitation.service.impl;

import com.daiba.invitation.dao.InvitationDao;
import com.daiba.invitation.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dolphinzhou on 2016/11/12.
 */
@Service("invitationService")
public class InvitationServiceImpl implements InvitationService{

    @Autowired
    private InvitationDao invitationDao;

    @Override
    public void createInvitationCode(List<String> invitationCodes) {
        for(String invitationCode : invitationCodes){
            invitationDao.insert(invitationCode);
        }
    }

    @Override
    public int useInvitationCode(String invitationCode) {
        String codeStatue = invitationDao.select(invitationCode);
        if(codeStatue!=null){
            if(codeStatue.equals("0")){
                return 1;
            }else{
                return 2;
            }
        }else{
            return 3;
        }
    }
}
