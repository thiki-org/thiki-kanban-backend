package org.thiki.kanban.password.password;

import org.springframework.stereotype.Repository;
import org.thiki.kanban.password.passwordReset.PasswordReset;
import org.thiki.kanban.password.passwordReset.PasswordResetApplication;
import org.thiki.kanban.password.passwordRetrieval.PasswordRetrievalApplication;

/**
 * Created by xubitao on 04/26/16.
 */

@Repository
public interface PasswordPersistence {
    Integer createPasswordRetrievalApplication(PasswordRetrievalApplication passwordRetrievalApplication);

    Integer createPasswordResetApplication(PasswordResetApplication passwordResetApplication);

    PasswordRetrievalApplication loadRetrievalApplication(PasswordResetApplication passwordResetApplication);

    Integer resetPassword(PasswordReset passwordReset);

    Integer cleanResetApplication(PasswordReset passwordReset);

    Integer makeRetrievalApplicationPassed(String email);

    PasswordReset loadResetApplicationByEmail(String email);

    Integer clearUnfinishedApplication(PasswordRetrievalApplication passwordRetrievalApplication);
}
