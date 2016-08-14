package org.thiki.kanban.foundation.exception;

import org.springframework.http.HttpStatus;

/**
 * @author joeaniu
 */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    /**
     * 错误代码， 比httpstatus更详细
     */
    private int code;

    private HttpStatus httpStatus;

    public BusinessException(String message) {
        this(ExceptionCode.UNKNOWN_EX.code(), message);
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public BusinessException(int code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    /**
     * http status in response.
     * should be overriden by sub class. default is 500.
     *
     * @return
     */
    public HttpStatus getStatus() {
        if (httpStatus != null) {
            return httpStatus;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
