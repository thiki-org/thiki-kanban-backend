package org.thiki.kanban.user;

/**
 * Created by xubt on 9/14/16.
 */
public enum UsersCodes {
    USER_IS_ALREADY_EXISTS(80011, "用户已经存在。"),
    USER_IS_NOT_EXISTS(80012, "用户不存在。");
    public static final String identityInRequired = "请提供用户标识:用户名或者密码。";
    private int code;
    private String message;

    UsersCodes(int code, String message) {
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
