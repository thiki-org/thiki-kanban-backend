package org.thiki.kanban.tag;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 11/7/16.
 */
public enum TagsCodes {
    SUMMARY_IS_EMPTY("001", "验收标准的概述不能为空。");
    public static final String summaryIsRequired = "验收标准的概述不能为空。";
    public static final String summaryIsInvalid = "卡片概述长度超限,请保持在200个字符以内。";
    private String code;
    private String message;

    TagsCodes(String code, String message) {
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
