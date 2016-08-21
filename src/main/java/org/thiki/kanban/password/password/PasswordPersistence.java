package org.thiki.kanban.password.password;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.thiki.kanban.password.passwordReset.PasswordResetApplication;
import org.thiki.kanban.password.passwordRetrieval.PasswordRetrievalApplication;

/**
 * Created by xubitao on 04/26/16.
 */

@Repository
public interface PasswordPersistence {
    Integer createPasswordRetrievalApplication(PasswordRetrievalApplication passwordRetrievalApplication);

    Integer createPasswordResetApplication(PasswordResetApplication passwordResetApplication);

    PasswordRetrievalApplication loadRetrievalApplication(String userName);

    Integer resetPassword(@Param("userName") String userName, @Param("password") String password);

    Integer cleanResetApplication(String userName);

    Integer makeRetrievalApplicationPassed(String userName);

    boolean isPasswordResetApplicationExists(String userName);

    Integer clearUnfinishedApplication(String userName);
}
