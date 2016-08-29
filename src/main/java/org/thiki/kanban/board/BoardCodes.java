package org.thiki.kanban.board;

/**
 * Created by xubt on 8/8/16.
 */
public enum BoardCodes {
    BOARD_IS_ALREADY_EXISTS(30011, "同名看板已经存在,请使用其它名称。"),
    BOARD_IS_NOT_EXISTS(30012, "看板不存在。");
    public static final String nameIsRequired = "看板名称不能为空。";
    public static final String nameIsInvalid = "看板名称过长,请保持在10个字以内。";
    private int code;
    private String message;

    BoardCodes(int code, String message) {
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
