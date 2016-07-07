package org.thiki.kanban.foundation.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by mac on 6/25/16.
 */
public class ResourceConflictException extends BusinessException {

    public ResourceConflictException(int code, String message) {
        super(code, message);
    }

    public ResourceConflictException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatus(){
        return HttpStatus.CONFLICT;
    }
}
