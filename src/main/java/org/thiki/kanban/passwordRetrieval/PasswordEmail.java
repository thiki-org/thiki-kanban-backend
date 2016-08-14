package org.thiki.kanban.passwordRetrieval;

import org.thiki.kanban.foundation.mail.MailEntity;

/**
 * Created by xubt on 8/14/16.
 */
public class PasswordEmail extends MailEntity {
    private String verificationCode;

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    @Override
    public String getSubject() {
        return "找回思奇看板密码";
    }
}
