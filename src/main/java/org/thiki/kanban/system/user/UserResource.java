package org.thiki.kanban.system.user;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.thiki.kanban.foundation.common.RestResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResource extends RestResource {

    public UserResource(User user) {
        this.domainObject = user;
        if (user != null) {
            Link selfLink = ControllerLinkBuilder.linkTo(methodOn(UsersController.class).findById(user.getId())).withSelfRel();
            this.add(selfLink);
        }
        this.add(ControllerLinkBuilder.linkTo(methodOn(UsersController.class).loadAll()).withRel("all"));
    }
    public UserResource() {
        this.add(linkTo(methodOn(UsersController.class).loadAll()).withRel("all"));
    }
}
