package org.thiki.kanban.teams.invitation;

/**
 * Created by xubt on 8/8/16.
 */
public enum InvitationCodes {
    TEAM_IS_NOT_EXISTS(70011, "团队不存在。"),
    INVITEE_IS_NOT_EXISTS(70012, "被邀请的成员不存在。");
    public static final String nameIsRequired = "团队名称不可以为空。";
    public static final String nameIsInvalid = "看板名称过长,请保持在10个字以内。";
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
