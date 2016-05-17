package org.thiki.kanban.foundation.exception;

public enum ExceptionCode {

    UNKONWN_EX (-99), 
    resourceNotFound(404),
    ;
    
    
    
    private int code;
    private ExceptionCode(int code){
        this.code = code;
    }
    
    public int code(){
        return code;
    }
    
}
