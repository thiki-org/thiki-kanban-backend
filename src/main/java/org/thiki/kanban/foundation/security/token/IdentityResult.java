package org.thiki.kanban.foundation.security.token;

/**
 * Created by xubt on 8/5/16.
 */
public class IdentityResult {
    private int errorCode;
    private String errorMessage;

    public static IdentityResult result(int errorCode, String errorMessage) {
        IdentityResult identityResult = new IdentityResult();
        identityResult.setErrorCode(errorCode);
        identityResult.setErrorMessage(errorMessage);
        return identityResult;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
