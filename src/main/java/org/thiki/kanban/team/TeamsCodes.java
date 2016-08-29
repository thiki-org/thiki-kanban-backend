package org.thiki.kanban.team;

/**
 * Created by xubt on 8/8/16.
 */
public enum TeamsCodes {
    TEAM_IS_ALREADY_EXISTS(50011, "同名看板已经存在,请使用其它名称。");
    public static final String nameIsRequired = "团队名称不可以为空。";
    public static final String nameIsInvalid = "看板名称过长,请保持在10个字以内。";
    private int code;
    private String message;

    TeamsCodes(int code, String message) {
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
