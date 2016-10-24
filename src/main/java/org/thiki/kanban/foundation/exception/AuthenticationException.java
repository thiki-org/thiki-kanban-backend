package org.thiki.kanban.foundation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author joeaniu
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    protected int code;

    private HttpStatus httpStatus;

    public AuthenticationException(Object codeObject) {
        super((String) ReflectionTestUtils.getField(codeObject, "message"));
        this.code = (int) ReflectionTestUtils.invokeGetterMethod(codeObject, "code");
    }

    public AuthenticationException(int code, String message) {
        super(message);
        this.code = code;
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }

    public AuthenticationException(int code, String message, HttpStatus httpStatus) {
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
        return HttpStatus.UNAUTHORIZED;
    }
}
