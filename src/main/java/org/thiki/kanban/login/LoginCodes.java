package org.thiki.kanban.login;

/**
 * Created by xubt on 8/8/16.
 */
public enum LoginCodes {
    USER_IS_NOT_EXISTS(10011, "用户 {0} 不存在,请先注册。"),
    USER_OR_PASSWORD_IS_INCORRECT(10012, "用户名或密码错误。");

    public static final String IdentityIsRequired = "登录失败,请输入您的用户名或邮箱。";
    public static final String PasswordIsRequired = "登录失败,您尚未输入密码。";

    private int code;
    private String message;

    LoginCodes(int code, String message) {
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
