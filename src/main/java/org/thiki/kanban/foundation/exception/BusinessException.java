package org.thiki.kanban.foundation.exception;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author joeaniu
 *
 */
public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    /** 错误代码， 比httpstatus更详细 */ 
    private int code;

    public BusinessException(String message) {
        this(ExceptionCode.UNKONWN_EX.code(), message);
    }
    
    public BusinessException(int code, String message){
        super(message);
        this.code = code;
    }
    
    public int getCode(){
        return code;
    }
    
    /**
     * http status in response.
     * should be overriden by sub class. default is 500.
     * @return
     */
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
