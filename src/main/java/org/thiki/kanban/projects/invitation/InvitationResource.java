package org.thiki.kanban.projects.invitation;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.projects.project.ProjectsController;

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

    public Object toResource(String userName, String projectId, Invitation invitation) throws Exception {
        InvitationResource invitationResource = new InvitationResource();
        invitationResource.domainObject = invitation;
        if (invitation != null) {
            Link selfLink = linkTo(methodOn(InvitationController.class).acceptInvitation(projectId, invitation.getId(), userName)).withSelfRel();
            invitationResource.add(tlink.from(selfLink).build(userName));

            Link projectLink = linkTo(methodOn(ProjectsController.class).findById(projectId, userName)).withRel("project");
            invitationResource.add(tlink.from(projectLink).build(userName));
        }
        return invitationResource.getResource();
    }
}
