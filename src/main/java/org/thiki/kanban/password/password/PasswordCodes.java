package org.thiki.kanban.password.password;

import org.thiki.kanban.foundation.application.DomainOrder;

/**
 * Created by xubt on 8/8/16.
 */
public enum PasswordCodes {
    EMAIL_IS_NOT_EXISTS("001", "邮箱不存在。"),
    SECURITY_CODE_TIMEOUT("002", "验证码已过期。"),
    NO_PASSWORD_RETRIEVAL_RECORD("003", "未找到密码找回申请记录,请核对你的邮箱或重新发送验证码。"),
    NO_PASSWORD_RESET_RECORD("004", "未找到密码重置申请记录。"),
    SECURITY_CODE_IS_NOT_CORRECT("005", "验证码错误。"),
    OLD_PASSWORD_NOT_EXISTS("006", "密码错误。");
    private String code;
    private String message;

    PasswordCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return Integer.parseInt(DomainOrder.PASSWORD + code);
    }

    public String message() {
        return message;
    }
}
