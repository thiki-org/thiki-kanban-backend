package org.thiki.kanban.teams.invitation;

import freemarker.template.TemplateException;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.mail.MailService;
import org.thiki.kanban.notification.Notification;
import org.thiki.kanban.notification.NotificationService;
import org.thiki.kanban.notification.NotificationType;
import org.thiki.kanban.teams.team.Team;
import org.thiki.kanban.teams.team.TeamsService;
import org.thiki.kanban.teams.teamMembers.TeamMembersService;
import org.thiki.kanban.user.User;
import org.thiki.kanban.user.UsersService;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


/**
 * Created by xutao on 9/12/16.
 */
@Service
public class InvitationService {
    public static final String NOTIFICATION_CONTENT = "%s邀请你加入%s";
    private static final String TEAM_INVITATION_TEMPLATE = "team-invitation-template.ftl";
    @Resource
    private InvitationPersistence invitationPersistence;
    @Resource
    private MailService mailService;
    @Resource
    private UsersService usersService;
    @Resource
    private TeamMembersService membersService;
    @Resource
    private TeamsService teamsService;
    @Resource
    private NotificationService notificationService;

    public Invitation invite(String userName, String teamId, Invitation invitation) throws TemplateException, IOException, MessagingException {
        Team team = teamsService.findById(teamId);
        if (team == null) {
            throw new BusinessException(InvitationCodes.TEAM_IS_NOT_EXISTS);
        }
        User inviteeUser = usersService.findByIdentity(invitation.getInvitee());
        if (inviteeUser == null) {
            throw new BusinessException(InvitationCodes.INVITEE_IS_NOT_EXISTS);
        }
        boolean isInviterLegal = membersService.isMember(teamId, userName);
        if (!isInviterLegal) {
            throw new BusinessException(InvitationCodes.INVITER_IS_NOT_A_MEMBER_OF_THE_TEAM);
        }

        boolean isInviteeAlreadyInTheTeam = membersService.isMember(teamId, inviteeUser.getName());
        if (isInviteeAlreadyInTheTeam) {
            throw new BusinessException(InvitationCodes.INVITEE_IS_ALREADY_A_MEMBER_OF_THE_TEAM);
        }
        if (!isInviterLegal) {
            throw new BusinessException(InvitationCodes.INVITER_IS_NOT_A_MEMBER_OF_THE_TEAM);
        }
        invitation.setInviter(userName);
        invitation.setTeamId(teamId);
        invitation.setInvitee(inviteeUser.getName());
        invitationPersistence.cancelPreviousInvitation(invitation);
        invitationPersistence.invite(invitation);


        InvitationEmail invitationEmail = new InvitationEmail();
        invitationEmail.setReceiver(inviteeUser.getEmail());
        invitationEmail.setInviter(userName);
        invitationEmail.setInvitee(inviteeUser.getName());
        invitationEmail.setTeamName(team.getName());

        Link invitationLink = linkTo(methodOn(InvitationController.class).acceptInvitation(teamId, invitation.getId())).withRel("invitationLink");
        invitationEmail.setInvitationLink(invitationLink.getHref());
        Notification notification = new Notification();
        notification.setReceiver(inviteeUser.getName());
        notification.setSender(userName);
        notification.setLink(invitationLink.getHref());
        notification.setContent(String.format(NOTIFICATION_CONTENT, userName, team.getName()));
        notification.setType(NotificationType.TEAM_MEMBER_INVITATION.type());
        notificationService.notify(notification);
        mailService.sendMailByTemplate(invitationEmail, TEAM_INVITATION_TEMPLATE);
        return invitationPersistence.findById(invitation.getId());
    }
}
