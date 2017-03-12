package org.thiki.kanban.login;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.board.BoardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.notification.NotificationController;
import org.thiki.kanban.projects.project.ProjectsController;
import org.thiki.kanban.user.UsersController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 7/8/16.
 */
@Service
public class IdentificationResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(Identification identification) throws Exception {
        IdentificationResource identificationResource = new IdentificationResource();
        identificationResource.domainObject = identification;

        Link projectsLink = linkTo(methodOn(ProjectsController.class).findByUserName(identification.getUserName())).withRel("projects");
        identificationResource.add(tlink.from(projectsLink).build());

        Link notificationsLink = linkTo(methodOn(NotificationController.class).loadUnreadNotificationsTotal(identification.getUserName())).withRel("unreadNotificationsTotal");
        identificationResource.add(tlink.from(notificationsLink).build());

        Link profileLink = linkTo(methodOn(UsersController.class).loadProfile(identification.getUserName())).withRel("profile");
        identificationResource.add(tlink.from(profileLink).build());
        Link avatarLink = linkTo(UsersController.class, UsersController.class.getMethod("loadAvatar", String.class, HttpServletResponse.class), identification.getUserName()).withRel("avatar");
        identificationResource.add(tlink.from(avatarLink).build(identification.getUserName()));

        return identificationResource.getResource();
    }
}
