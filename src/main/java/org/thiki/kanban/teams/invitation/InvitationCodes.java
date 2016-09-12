package org.thiki.kanban.teams.invitation;

/**
 * Created by xubt on 8/8/16.
 */
public enum InvitationCodes {
    TEAM_IS_NOT_EXISTS(70011, "团队不存在。"),
    INVITEE_IS_NOT_EXISTS(70012, "被邀请的成员不存在。"),
    INVITER_IS_NOT_A_MEMBER_OF_THE_TEAM(70013, "邀请人并不是当前团队的成员,不允许邀请他人进入该团队。");
    public static final String InviteeIsRequired = "请指定被邀请的成员。";
    private int code;
    private String message;

    InvitationCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
