package org.thiki.kanban.password;

/**
 * Created by xubt on 8/8/16.
 */
public enum PasswordRetrievalCodes {
    EMAIL_IS_NOT_EXISTS(20011, "邮箱不存在。"),
    SECURITY_CODE_TIMEOUT(20012, "验证码已过期。"),
    NO_PASSWORD_RETRIEVAL_RECORD(20013, "未找到密码找回申请记录,请核对你的邮箱或重新发送验证码。"),
    NO_PASSWORD_RESET_RECORD(20014, "未找到密码重置申请记录。"),
    SECURITY_CODE_IS_NOT_CORRECT(20015, "验证码错误。");
    private int code;
    private String message;

    PasswordRetrievalCodes(int code, String message) {
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
