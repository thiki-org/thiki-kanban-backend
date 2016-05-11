package org.thiki.kanban.foundation.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BusinessException{

    private static final long serialVersionUID = -5266231525138811045L;

    public ResourceNotFoundException(String message) {
        super(ExceptionCode.resourceNotFound.code(), message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
    
    

    

}
