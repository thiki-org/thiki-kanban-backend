package org.thiki.kanban.teams.invitation;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.teams.team.TeamsController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by bogehu on 9/12/16.
 */
@Service
public class InvitationResource extends RestResource {

    @Resource
    private TLink tlink;

    public Object toResource(String userName, String teamId, Invitation invitation) throws Exception {
        InvitationResource invitationResource = new InvitationResource();
        invitationResource.domainObject = invitation;
        if (invitation != null) {
            Link selfLink = linkTo(methodOn(InvitationController.class).acceptInvitation(teamId, invitation.getId(), userName)).withSelfRel();
            invitationResource.add(tlink.from(selfLink).build(userName));

            Link teamLink = linkTo(methodOn(TeamsController.class).findById(teamId, userName)).withRel("team");
            invitationResource.add(tlink.from(teamLink).build(userName));
        }
        return invitationResource.getResource();
    }
}
