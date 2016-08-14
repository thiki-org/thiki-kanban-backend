package org.thiki.kanban.passwordRetrieval;

/**
 * Created by xubt on 8/8/16.
 */
public enum PasswordRetrievalCodes {
    EmailIsNotExists(20011, "邮箱不存在。");
    private int code;
    private String message;

    private PasswordRetrievalCodes(int code, String message) {
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
