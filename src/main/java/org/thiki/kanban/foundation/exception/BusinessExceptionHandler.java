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
public class BusinessExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handle(BusinessException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        BusinessException be = ex;
        body.put("message", be.getMessage());
        body.put("code", ex.getCode());

        HttpStatus httpStatus = ex.getStatus();

        return handleExceptionInternal(ex, body, new HttpHeaders(), httpStatus, request);
    }
}
