package org.thiki.kanban.passwordRetrieval;

import org.springframework.stereotype.Repository;

/**
 * Created by xubitao on 04/26/16.
 */

@Repository
public interface PasswordRetrievalPersistence {
    Integer createRecord(PasswordRetrieval passwordRetrieval);

    Integer createPasswordResetApplication(PasswordResetApplication passwordResetApplication);

    PasswordRetrieval verify(PasswordResetApplication passwordResetApplication);

    Integer resetPassword(PasswordReset passwordReset);

    Integer cleanResetPasswordRecord(PasswordReset passwordReset);
}
