package org.thiki.kanban.acceptanceCriteria;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 10/17/16.
 */
public enum AcceptCriteriaCodes {
    CARD_IS_NOT_EXISTS("001", "卡片未找到。");
    public static final String summaryIsRequired = "卡片概述不能为空。";
    public static final String summaryIsInvalid = "卡片概述长度超限,请保持在50个字符以内。";
    private String code;
    private String message;

    AcceptCriteriaCodes(String code, String message) {
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
