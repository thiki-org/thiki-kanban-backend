package org.thiki.kanban.teams.invitation;

import freemarker.template.TemplateException;
import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.teams.team.TeamsController;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by bogehu on 9/12/16.
 */
public class InvitationResource extends RestResource {

    public InvitationResource(String userName, String teamId, Invitation savedInvitation) throws TemplateException, IOException, MessagingException {
        this.domainObject = savedInvitation;
        if (savedInvitation != null) {
            Link selfLink = linkTo(methodOn(InvitationController.class).invite(savedInvitation, teamId, userName)).withSelfRel();
            this.add(selfLink);

            Link teamLink = linkTo(methodOn(TeamsController.class).findById(teamId, userName)).withRel("team");
            this.add(teamLink);
        }
    }
}
