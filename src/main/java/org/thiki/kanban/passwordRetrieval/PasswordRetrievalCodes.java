package org.thiki.kanban.passwordRetrieval;

/**
 * Created by xubt on 8/8/16.
 */
public enum PasswordRetrievalCodes {
    EMAIL_IS_NOT_EXISTS(20011, "邮箱不存在。"),
    SECURITY_CODE_TIMEOUT(20012, "验证码已过期。");
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
