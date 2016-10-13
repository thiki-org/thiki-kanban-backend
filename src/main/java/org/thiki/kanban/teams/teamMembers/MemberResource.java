package org.thiki.kanban.teams.teamMembers;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.user.UsersController;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by 濤 on 7/26/16.
 */
public class MemberResource extends RestResource {
    public MemberResource(Member member) throws IOException {
        this.domainObject = member;
        Link profileLink = linkTo(methodOn(UsersController.class).loadProfile(member.getUserName())).withRel("profile");
        this.add(profileLink);
    }
}
