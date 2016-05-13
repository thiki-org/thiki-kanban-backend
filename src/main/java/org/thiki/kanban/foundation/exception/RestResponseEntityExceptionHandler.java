package org.thiki.kanban.foundation.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(value = { BusinessException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        BusinessException be = (BusinessException) ex;
        body.put("message", be.getMessage());
        body.put("code", be.getCode());
        
        HttpStatus httpStatus = be.getStatus();
        
        return handleExceptionInternal(ex, body, 
                new HttpHeaders(), httpStatus, request);
    }
}