package org.thiki.kanban.verification;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by skytao on 03/06/17.
 */
public enum VerificationCodes {
    ACCEPTANCE_CRITERIA_IS_NOT_FINISHED("001", "验收标准尚未完成，不能核验。"),
    ACCEPTANCE_CRITERIA_IS_NOT_FOUND("002", "验收标准不存在。"),
    CARD_HAS_ALREADY_BEEN_ARCHIVED_OR_DONE("003", "验收标准所属卡片已经处于完成或归档环节，不允许再核验。");
    public static final String IS_PASSED_NOT_VALID = "请指定核验是否已经通过。";
    public static final String REMARK_IS_NOT_VALID = "核验意见不能为空且不能超过100个字符。";
    public static final String ACCEPTANCE_CRITERIA_ID__IS_NOT_VALID = "待核验的验收标准未指定。";
    private String code;
    private String message;

    VerificationCodes(String code, String message) {
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
