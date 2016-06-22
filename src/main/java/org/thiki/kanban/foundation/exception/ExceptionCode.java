package org.thiki.kanban.foundation.exception;

public enum ExceptionCode {

    UNKNOWN_EX(-99),
    INVALID_PARAMS(400),
    resourceNotFound(404),
    USER_EXISTS(100101),
    ;

    private int code;

    private ExceptionCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
