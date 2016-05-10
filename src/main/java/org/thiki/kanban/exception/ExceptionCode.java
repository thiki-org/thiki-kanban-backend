package org.thiki.kanban.exception;

public enum ExceptionCode {

    UNKONWN_EX (-99), 
    notFound (1),
    ;
    
    
    
    private int code;
    private ExceptionCode(int code){
        this.code = code;
    }
    
    public int code(){
        return code;
    }
    
}
