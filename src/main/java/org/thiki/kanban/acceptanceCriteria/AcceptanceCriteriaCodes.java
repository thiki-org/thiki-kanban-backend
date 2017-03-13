package org.thiki.kanban.acceptanceCriteria;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 10/17/16.
 */
public enum AcceptanceCriteriaCodes {
    SUMMARY_IS_EMPTY("001", "验收标准的概述不能为空。"), ACCEPTANCE_CRITERIA_IS_NOT_FOUND("002", "验收标准不存在。");
    public static final String summaryIsRequired = "验收标准的概述不能为空。";
    public static final String summaryIsInvalid = "卡片概述长度超限,请保持在200个字符以内。";
    public static final Integer STATUS_UNVERIFIED = 0;
    public static final Integer STATUS_UNPASSED = -1;
    public static final Integer STATUS_PASSED = 1;
    private String code;
    private String message;

    AcceptanceCriteriaCodes(String code, String message) {
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
