package org.thiki.kanban.exception;

public class BusinessException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    
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
}
