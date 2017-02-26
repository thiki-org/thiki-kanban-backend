package org.thiki.kanban.user;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.FileUtil;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 28/09/2016.
 */
@Service
public class AvatarResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(String userName) throws Exception {
        AvatarResource avatarResource = new AvatarResource();
        Link selfLink = linkTo(methodOn(UsersController.class).uploadAvatar(userName, null)).withSelfRel();
        avatarResource.add(tlink.from(selfLink).build(userName));

        Link profileLink = linkTo(methodOn(UsersController.class).loadProfile(userName)).withRel("profile");
        avatarResource.add(tlink.from(profileLink).build(userName));
        return avatarResource.getResource();
    }

    @Cacheable(value = "avatar", key = "'avatar'+#userName")
    public Object toResource(String userName, File avatar) throws Exception {
        AvatarResource avatarResource = new AvatarResource();
        avatarResource.buildDataObject("avatar", FileUtil.fileString(avatar));
        Link selfLink = linkTo(methodOn(UsersController.class).uploadAvatar(userName, null)).withSelfRel();
        avatarResource.add(tlink.from(selfLink).build(userName));
        return avatarResource.getResource();
    }
}
