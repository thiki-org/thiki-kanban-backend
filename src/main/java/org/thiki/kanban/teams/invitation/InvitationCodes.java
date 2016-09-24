package org.thiki.kanban.teams.invitation;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 8/8/16.
 */
public enum InvitationCodes {
    TEAM_IS_NOT_EXISTS("001", "团队不存在。"),
    INVITEE_IS_NOT_EXISTS("002", "被邀请的成员不存在。"),
    INVITER_IS_NOT_A_MEMBER_OF_THE_TEAM("003", "邀请人并不是当前团队的成员,不允许邀请他人进入该团队。"),
    INVITEE_IS_ALREADY_A_MEMBER_OF_THE_TEAM("004", "邀请对象已经是该团队成员,无须再次邀请。"),
    INVITATION_IS_NOT_EXIST("005", "邀请函不存在或已失效。"),
    INVITATION_IS_ALREADY_ACCEPTED("006", "您此前已经接受邀请。");
    public static final String InviteeIsRequired = "请指定被邀请的成员。";
    private String code;
    private String message;

    InvitationCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.INVITATION + code);
    }

    public String message() {
        return message;
    }
}
