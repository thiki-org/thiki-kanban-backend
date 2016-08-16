package org.thiki.kanban.password;

import org.springframework.stereotype.Repository;

/**
 * Created by xubitao on 04/26/16.
 */

@Repository
public interface PasswordRetrievalPersistence {
    Integer createPasswordRetrievalApplication(PasswordRetrievalApplication passwordRetrievalApplication);

    Integer createPasswordResetApplication(PasswordResetApplication passwordResetApplication);

    PasswordRetrievalApplication verify(PasswordResetApplication passwordResetApplication);

    Integer resetPassword(PasswordReset passwordReset);

    Integer cleanResetPasswordRecord(PasswordReset passwordReset);

    Integer passSecurityCodeVerification(String email);

    PasswordReset findPasswordResetByEmail(String email);

    Integer clearUnfinishedApplication(PasswordRetrievalApplication passwordRetrievalApplication);
}
