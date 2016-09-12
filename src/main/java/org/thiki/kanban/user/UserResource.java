package org.thiki.kanban.user;

import org.springframework.hateoas.Link;
import org.thiki.kanban.board.BoardsController;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResource extends RestResource {

    public UserResource(UserProfile userProfile) {
        this.domainObject = userProfile;
        if (userProfile != null) {
            Link selfLink = linkTo(methodOn(UsersController.class).findById(userProfile.getId())).withSelfRel();
            Link boardsLink = linkTo(methodOn(BoardsController.class).loadByUserName(userProfile.getId())).withRel("boards");
            this.add(boardsLink);
            this.add(selfLink);
        }
        this.add(linkTo(methodOn(UsersController.class).loadAll()).withRel("users"));
    }

    public UserResource() {
        this.add(linkTo(methodOn(UsersController.class).loadAll()).withRel("users"));
    }
}
