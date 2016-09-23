package org.thiki.kanban.teams.invitation;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by xutao on 9/11/16.
 */

public class Invitation {
    private String id;
    @NotEmpty(message = InvitationCodes.InviteeIsRequired)
    @NotNull(message = InvitationCodes.InviteeIsRequired)
    private String invitee;
    private String inviter;
    private String teamId;
    private Integer isAccepted;
    private String creationTime;
    private String modificationTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getInvitee() {
        return invitee;
    }

    public void setInvitee(String invitee) {
        this.invitee = invitee;
    }

    public boolean getIsAccepted() {
        return (isAccepted == null || isAccepted == 0) ? false : true;
    }

    public void setIsAccepted(Integer isAccepted) {
        this.isAccepted = isAccepted;
    }
}
