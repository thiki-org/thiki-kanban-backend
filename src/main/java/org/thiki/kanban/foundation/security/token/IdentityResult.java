package org.thiki.kanban.foundation.security.token;

/**
 * Created by xubt on 8/5/16.
 */
public class IdentityResult {
    private String errorCode;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static IdentityResult result(String errorCode, String errorMessage) {
        IdentityResult identityResult = new IdentityResult();
        identityResult.setErrorCode(errorCode);
        identityResult.setErrorMessage(errorMessage);
        return identityResult;
    }
}
