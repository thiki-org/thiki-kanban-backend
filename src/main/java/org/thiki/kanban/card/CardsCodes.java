package org.thiki.kanban.card;

/**
 * Created by xubt on 8/8/16.
 */
public enum CardsCodes {
    CARD_IS_NOT_EXISTS(40011, "卡片未找到。");
    private int code;
    private String message;
    public static final String summaryIsRequired = "卡片概述不能为空。";
    public static final String summaryIsInvalid = "卡片概述长度超限,请保持在50个字符以内。";

    CardsCodes(int code, String message) {
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
