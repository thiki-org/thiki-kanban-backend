package org.thiki.kanban.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 30/09/2016.
 */
@Service
public class ProfileResource extends RestResource {
    @Resource
    private TLink tlink;

    @Cacheable(value = "profile", key = "'profile'+#userName")
    public Object toResource(Profile profile, String userName) throws Exception {
        ProfileResource profileResource = new ProfileResource();
        profileResource.domainObject = profile;
        Link selfLink = linkTo(methodOn(UsersController.class).loadProfile(profile.getUserName())).withSelfRel();
        profileResource.add(tlink.from(selfLink).build(userName));
        Link avatarLink = linkTo(UsersController.class, UsersController.class.getMethod("loadAvatar", String.class, HttpServletResponse.class), profile.getUserName()).withRel("avatar");
        profileResource.add(tlink.from(avatarLink).build(userName));
        return profileResource.getResource();
    }
}
