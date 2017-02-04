package org.thiki.kanban.projects.invitation;

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
    private String projectId;
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
        return projectId;
    }

    public void setTeamId(String projectId) {
        this.projectId = projectId;
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
