package org.thiki.kanban.teams.teamMembers;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 8/8/16.
 */
public enum TeamMembersCodes {
    CURRENT_USER_IS_NOT_A_MEMBER_OF_THE_TEAM("001", "当前用户非该团队成员,允许获取。");

    private String code;
    private String message;

    TeamMembersCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.TEAM_MEMBER + "" + code);
    }

    public String message() {
        return message;
    }
}
