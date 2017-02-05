package org.thiki.kanban.sprint;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 04/02/2017.
 */
public enum SprintCodes {
    START_TIME_IS_AFTER_END_TIME("001", "迭代开始日期晚于结束日期。"), UNARCHIVE_SPRINT_EXIST("002", "存在尚未归档的迭代，不允许创建新的迭代。");
    public static final int IN_PROGRESS = 1;
    public static final String startTimeIsRequired = "迭代开始时间不可以为空。";
    public static final String endTimeIsRequired = "迭代结束时间不可以为空。";
    private String code;

    private String message;

    SprintCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.CARD + "" + code);
    }

    public String message() {
        return message;
    }
}
