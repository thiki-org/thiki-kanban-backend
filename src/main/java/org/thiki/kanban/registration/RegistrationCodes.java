package org.thiki.kanban.registration;

/**
 * Created by xubt on 9/14/16.
 */
public enum RegistrationCodes {
    USERNAME_IS_ALREADY_EXISTS(10011, "用户名已经被使用,请使用其他用户名。"),
    EMAIL_IS_ALREADY_EXISTS(10012, "邮箱已经存在,请使用其他邮箱。");
    private int code;
    private String message;

    RegistrationCodes(int code, String message) {
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
