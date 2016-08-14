package org.thiki.kanban.passwordRetrieval;

import org.springframework.stereotype.Repository;

/**
 * Created by xubitao on 04/26/16.
 */

@Repository
public interface PasswordRetrievalPersistence {
    Boolean existsEmail(String email);
}
