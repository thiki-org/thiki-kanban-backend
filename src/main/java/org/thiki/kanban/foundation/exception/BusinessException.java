package org.thiki.kanban.foundation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author joeaniu
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int code;

    private HttpStatus httpStatus;

    public BusinessException(String message) {
        this(ExceptionCode.UNKNOWN_EX.code(), message);
    }

    public BusinessException(Object codeObject) {
        super((String) ReflectionTestUtils.getField(codeObject, "message"));
        this.code = (int) ReflectionTestUtils.getField(codeObject, "code");
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

    public HttpStatus getStatus() {
        if (httpStatus != null) {
            return httpStatus;
        }
        return HttpStatus.BAD_REQUEST;
    }
}
