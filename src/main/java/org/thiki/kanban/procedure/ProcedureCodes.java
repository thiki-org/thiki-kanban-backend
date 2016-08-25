package org.thiki.kanban.procedure;

/**
 * Created by xubt on 8/8/16.
 */
public enum ProcedureCodes {
    BOARD_IS_ALREADY_EXISTS(30011, "同名看板已经存在,请使用其它名称。");
    private int code;
    private String message;
    public static final String titleIsRequired = "工序名称不能为空。";
    public static final String titleIsInvalid = "工序名称长度?超限,请保持在10个汉字以内。";

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
