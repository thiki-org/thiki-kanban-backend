package org.thiki.kanban.user;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 28/09/2016.
 */
public class AvatarResource extends RestResource {
    public AvatarResource(String userName) throws IOException {
        Link selfLink = linkTo(methodOn(UsersController.class).uploadAvatar(userName, null)).withSelfRel();
        this.add(selfLink);

        Link userLink = linkTo(methodOn(UsersController.class).loadUserByName(userName)).withRel("user");
        this.add(userLink);
    }
}
