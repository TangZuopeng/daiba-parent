package com.daiba.invitation.mode;

/**
 * Created by dolphinzhou on 2016/11/12.
 */
public class Invitation {

    private int invitationId;
    private String invitationCode;
    private String codeStatue;

    public int getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(int invitationId) {
        this.invitationId = invitationId;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getCodeStatue() {
        return codeStatue;
    }

    public void setCodeStatue(String codeStatue) {
        this.codeStatue = codeStatue;
    }

    public Invitation() {
    }

    public Invitation(String invitationCode, String codeStatue) {
        super();
        this.invitationCode = invitationCode;
        this.codeStatue = codeStatue;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "invitationId=" + invitationId +
                ", invitationCode='" + invitationCode + '\'' +
                ", codeStatue=" + codeStatue +
                '}';
    }
}
