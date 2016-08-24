package org.thiki.kanban.foundation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by xubt on 8/24/16.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)

public class InvalidParamsException extends BusinessException {

    private static final long serialVersionUID = -5266231525138811045L;

    public InvalidParamsException(int errorCode, String message) {
        super(errorCode, message, HttpStatus.BAD_REQUEST);
    }
}
