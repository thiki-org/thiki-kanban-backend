package org.thiki.kanban.projects.invitation;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.mail.MailService;
import org.thiki.kanban.notification.Notification;
import org.thiki.kanban.notification.NotificationService;
import org.thiki.kanban.notification.NotificationType;
import org.thiki.kanban.projects.members.MembersService;
import org.thiki.kanban.projects.project.Project;
import org.thiki.kanban.projects.project.ProjectCodes;
import org.thiki.kanban.projects.project.ProjectsService;
import org.thiki.kanban.user.User;
import org.thiki.kanban.user.UsersService;

import javax.annotation.Resource;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


/**
 * Created by xutao on 9/12/16.
 */
@Service
public class InvitationService {
    public static final String NOTIFICATION_CONTENT = "%s邀请你加入%s";
    private static final String PROJECT_INVITATION_TEMPLATE = "project-invitation-template.ftl";
    @Resource
    private InvitationPersistence invitationPersistence;
    @Resource
    private MailService mailService;
    @Resource
    private UsersService usersService;
    @Resource
    private MembersService membersService;
    @Resource
    private ProjectsService projectsService;
    @Resource
    private NotificationService notificationService;

    public Invitation invite(String userName, String projectId, Invitation invitation) throws Exception {
        Project project = projectsService.findById(projectId);
        if (project == null) {
            throw new BusinessException(ProjectCodes.PROJECT_IS_NOT_EXISTS);
        }
        User inviteeUser = usersService.findByIdentity(invitation.getInvitee());
        if (inviteeUser == null) {
            throw new BusinessException(InvitationCodes.INVITEE_IS_NOT_EXISTS);
        }
        boolean isInviterLegal = membersService.isMember(projectId, userName);
        if (!isInviterLegal) {
            throw new BusinessException(InvitationCodes.INVITER_IS_NOT_A_MEMBER_OF_THE_PROJECT);
        }

        boolean isInviteeAlreadyInTheTeam = membersService.isMember(projectId, inviteeUser.getUserName());
        if (isInviteeAlreadyInTheTeam) {
            throw new BusinessException(InvitationCodes.INVITEE_IS_ALREADY_A_MEMBER_OF_THE_PROJECT);
        }
        if (!isInviterLegal) {
            throw new BusinessException(InvitationCodes.INVITER_IS_NOT_A_MEMBER_OF_THE_PROJECT);
        }
        invitation.setInviter(userName);
        invitation.setTeamId(projectId);
        invitation.setInvitee(inviteeUser.getUserName());
        invitationPersistence.cancelPreviousInvitation(invitation);
        invitationPersistence.invite(invitation);


        InvitationEmail invitationEmail = new InvitationEmail();
        invitationEmail.setReceiverEmailAddress(inviteeUser.getEmail());
        invitationEmail.setInviter(userName);
        invitationEmail.setInvitee(inviteeUser.getUserName());
        invitationEmail.setProjectName(project.getName());

        Link invitationLink = linkTo(methodOn(InvitationController.class).acceptInvitation(projectId, invitation.getId(), userName)).withRel("invitationLink");
        invitationEmail.setInvitationLink(invitationLink.getHref());
        Notification notification = new Notification();
        notification.setReceiver(inviteeUser.getUserName());
        notification.setSender(userName);
        notification.setLink(invitationLink.getHref());
        notification.setContent(String.format(NOTIFICATION_CONTENT, userName, project.getName()));
        notification.setType(NotificationType.PROJECT_MEMBER_INVITATION.type());
        notificationService.notify(notification);
        mailService.sendMailByTemplate(invitationEmail, PROJECT_INVITATION_TEMPLATE);
        return invitationPersistence.findById(invitation.getId());
    }

    public Invitation loadInvitation(String invitationId) {
        Invitation invitation = invitationPersistence.findById(invitationId);
        if (invitation == null) {
            throw new BusinessException(InvitationCodes.INVITATION_IS_NOT_EXIST);
        }
        return invitation;
    }

    public Invitation acceptInvitation(String userName, String projectId, String invitationId) {
        Invitation invitation = invitationPersistence.findById(invitationId);
        if (invitation == null) {
            throw new BusinessException(InvitationCodes.INVITATION_IS_NOT_EXIST);
        }
        if (invitation.getIsAccepted()) {
            throw new BusinessException(InvitationCodes.INVITATION_IS_ALREADY_ACCEPTED);
        }
        invitationPersistence.acceptInvitation(invitation.getInvitee(), projectId);
        membersService.joinTeam(userName, projectId);
        return invitationPersistence.findById(invitationId);
    }
}
