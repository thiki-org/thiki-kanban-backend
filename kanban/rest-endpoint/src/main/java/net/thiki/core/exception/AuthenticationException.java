package net.thiki.core.exception;


public class AuthenticationException extends BusinessException {

    private static final long serialVersionUID = 1L;

    public AuthenticationException(String msg) {
        super(msg);
    }
    
    public AuthenticationException() {
    
    }


}
