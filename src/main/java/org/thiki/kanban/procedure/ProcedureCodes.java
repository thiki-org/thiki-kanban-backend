package org.thiki.kanban.procedure;

/**
 * Created by xubt on 8/8/16.
 */
public enum ProcedureCodes {
    TITLE_IS_ALREADY_EXISTS(30011, "该名称已经被使用,请使用其它名称。");
    public static final String titleIsRequired = "工序名称不能为空。";
    public static final String titleIsInvalid = "工序名称长度超限,请保持在30个字符以内。";
    private int code;
    private String message;

    ProcedureCodes(int code, String message) {
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
