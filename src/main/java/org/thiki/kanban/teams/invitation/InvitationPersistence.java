package org.thiki.kanban.teams.invitation;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by bogehu on 7/11/16.
 */
@Repository
public interface InvitationPersistence {
    Integer invite(Invitation invitation);

    Invitation findById(String id);

    Integer cancelPreviousInvitation(Invitation invitation);

    Integer acceptInvitation(@Param("invitee") String invitee, @Param("invitationId") String invitationId);
}
