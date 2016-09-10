package org.thiki.kanban.teamMembers;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.user.UsersController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by 濤 on 7/26/16.
 */
public class MemberResource extends RestResource {
    public MemberResource(Member member) {
        this.domainObject = member;
        if (member != null) {
            Link selfLink = linkTo(methodOn(UsersController.class).findById(member.getUserName())).withSelfRel();
            this.add(selfLink);
        }
    }
}
