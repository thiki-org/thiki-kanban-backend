package org.thiki.kanban.user;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 30/09/2016.
 */
public class ProfileResourceResource extends RestResource {
    public ProfileResourceResource(UserProfile profile) throws IOException {
        this.domainObject = profile;
        Link selfLink = linkTo(methodOn(UsersController.class).loadProfile(profile.getUserName())).withSelfRel();
        this.add(selfLink);

        Link avatarLink = linkTo(methodOn(UsersController.class).loadAvatar(profile.getUserName())).withRel("avatar");
        this.add(avatarLink);
    }
}
