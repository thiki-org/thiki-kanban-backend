package org.thiki.kanban.passwordRetrieval;

import java.util.Date;

/**
 * Created by xubt on 8/14/16.
 */
public class PasswordRetrieval {
    private String id;

    private String email;

    private String verificationCode;
    private Date modificationTime;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
