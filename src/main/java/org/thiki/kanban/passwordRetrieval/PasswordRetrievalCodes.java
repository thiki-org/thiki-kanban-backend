package org.thiki.kanban.passwordRetrieval;

/**
 * Created by xubt on 8/8/16.
 */
public enum PasswordRetrievalCodes {
    ParamInvalid("0011", "Param is invalid");
    private String code;
    private String message;


    private PasswordRetrievalCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return getSN() + code;
    }

    public String message() {
        return message;
    }

    private String getSN() {
        return "2";
    }
}
