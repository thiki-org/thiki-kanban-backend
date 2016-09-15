package org.thiki.kanban.teams.team;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 8/8/16.
 */
public enum TeamsCodes {
    TEAM_IS_ALREADY_EXISTS("001", "同名团队已经存在。"),
    TEAM_IS_NOT_EXISTS("002", "团队不存在。");
    public static final String nameIsRequired = "团队名称不可以为空。";
    public static final String nameIsInvalid = "看板名称过长,请保持在10个字以内。";
    private String code;
    private String message;

    TeamsCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.TEAM + "" + code);
    }

    public String message() {
        return message;
    }
}
