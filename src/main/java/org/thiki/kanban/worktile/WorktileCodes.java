package org.thiki.kanban.worktile;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 11/1/16.
 */
public enum WorktileCodes {
    BOARD_IS_ALREADY_EXISTS("001", "同名看板已经存在,请使用其它名称。"),
    BOARD_IS_NOT_EXISTS("002", "看板不存在。"),
    FORBID_CURRENT_IS_NOT_A_MEMBER_OF_THE_TEAM("003", "操作被阻止!你非当前看板所属团队成员,看板亦非你个人所有。");
    public static final String nameIsRequired = "看板名称不能为空。";
    public static final String nameIsInvalid = "看板名称过长,请保持在10个字以内。";
    private String code;
    private String message;

    WorktileCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.BOARD + code);
    }

    public String message() {
        return message;
    }
}
