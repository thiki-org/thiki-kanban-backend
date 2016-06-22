package org.thiki.kanban.foundation.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        BusinessException be = (BusinessException) ex;
        body.put("message", be.getMessage());
        body.put("code", be.getCode());

        HttpStatus httpStatus = be.getStatus();

        return handleExceptionInternal(ex, body,
                new HttpHeaders(), httpStatus, request);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<?> handleOtherException(RuntimeException ex, WebRequest request) {
        ex.printStackTrace();
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("code", ExceptionCode.UNKNOWN_EX);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
