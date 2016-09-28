package org.thiki.kanban.user;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 9/14/16.
 */
public enum UsersCodes {
    USERNAME_IS_ALREADY_EXISTS("001", "用户名已经被使用,请使用其他用户名。"),
    EMAIL_IS_ALREADY_EXISTS("002", "邮箱已经存在,请使用其他邮箱。"), AVATAR_IS_EMPTY("003", "请上传头像文件。");
    public static final String identityIsRequired = "请提供用户标识:用户名或者密码。";
    public static final String emailIsRequired = "邮箱不可以为空。";
    public static final String userNameIsRequired = "用户名不可以为空。";
    public static final String md5PasswordIsRequired = "MD5密码不可以为空。";
    private String code;
    private String message;

    UsersCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.REGISTRATION + "" + code);
    }

    public String message() {
        return message;
    }
}
