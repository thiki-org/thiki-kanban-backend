package org.thiki.kanban.user;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 30/09/2016.
 */
@Service
public class ProfileResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(UserProfile profile, String userName) throws IOException {
        ProfileResource profileResource = new ProfileResource();
        profileResource.domainObject = profile;
        Link selfLink = linkTo(methodOn(UsersController.class).loadProfile(profile.getUserName())).withSelfRel();
        profileResource.add(tlink.from(selfLink).build(userName));

        Link avatarLink = linkTo(methodOn(UsersController.class).loadAvatar(profile.getUserName())).withRel("avatar");
        profileResource.add(tlink.from(avatarLink).build(userName));
        return profileResource.getResource();
    }
}
