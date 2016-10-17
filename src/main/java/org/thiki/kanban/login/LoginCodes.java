package org.thiki.kanban.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 8/8/16.
 */
public enum LoginCodes {
    USER_IS_NOT_EXISTS("001", "用户不存在,请先注册。"),
    USER_OR_PASSWORD_IS_INCORRECT("002", "用户名或密码错误。");

    public static final String IdentityIsRequired = "登录失败,请输入您的用户名或邮箱。";
    public static final String PasswordIsRequired = "登录失败,您尚未输入密码。";


    private String code;
    private String message;

    LoginCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.LOGIN + code);
    }

    public String message() {
        return message;
    }
}
