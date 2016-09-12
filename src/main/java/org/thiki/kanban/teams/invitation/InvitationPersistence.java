package org.thiki.kanban.teams.invitation;

import org.springframework.stereotype.Repository;

/**
 * Created by bogehu on 7/11/16.
 */
@Repository
public interface InvitationPersistence {
    Integer invite(Invitation invitation);

    Invitation findById(String id);
}
