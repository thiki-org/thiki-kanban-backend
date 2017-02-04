package org.thiki.kanban.projects.invitation;

import org.thiki.kanban.foundation.mail.MailEntity;

/**
 * Created by xubt on 8/14/16.
 */
public class InvitationEmail extends MailEntity {
    private String inviter;
    private String invitee;
    private String projectName;
    private String invitationLink;

    @Override
    public String getSubject() {
        return "邀请加入团队";
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getInvitee() {
        return invitee;
    }

    public void setInvitee(String invitee) {
        this.invitee = invitee;
    }

    public String getTeamName() {
        return projectName;
    }

    public void setTeamName(String projectName) {
        this.projectName = projectName;
    }

    public String getInvitationLink() {
        return invitationLink;
    }

    public void setInvitationLink(String invitationLink) {
        this.invitationLink = invitationLink;
    }
}
