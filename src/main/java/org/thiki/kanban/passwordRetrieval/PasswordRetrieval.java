package org.thiki.kanban.passwordRetrieval;

/**
 * Created by xubt on 8/14/16.
 */
public class PasswordRetrieval {
    private String id;

    private String email;

    private String verificationCode;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
