package org.thiki.kanban.foundation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BusinessException {

    private static final long serialVersionUID = -5266231525138811045L;

    public ResourceNotFoundException(String message) {
        super(ExceptionCode.resourceNotFound.code(), message);
    }

    public ResourceNotFoundException(Object codeObject) {
        super(codeObject);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
