package org.thiki.kanban.teamMembers;

/**
 * Created by xubt on 8/8/16.
 */
public enum TeamMembersCodes {
    CURRENT_USER_IS_NOT_A_MEMBER_OF_THE_TEAM(60011, "当前用户非该团队成员,允许获取。");

    private int code;
    private String message;

    TeamMembersCodes(int code, String message) {
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
