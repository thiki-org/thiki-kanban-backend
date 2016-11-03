package org.thiki.kanban.teams.teamMembers;

import org.springframework.hateoas.Link;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.teams.team.TeamsController;
import org.thiki.kanban.user.UsersController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by 濤 on 7/26/16.
 */
public class MemberResource extends RestResource {
    public MemberResource(String teamId, Member member) throws Exception {
        this.domainObject = member;
        Link profileLink = linkTo(methodOn(UsersController.class).loadProfile(member.getUserName())).withRel("profile");
        this.add(profileLink);

        Link avatarLink = linkTo(methodOn(UsersController.class).loadAvatar(member.getUserName())).withRel("avatar");
        this.add(avatarLink);

        Link selfLink = linkTo(methodOn(TeamMembersController.class).getMember(teamId, member.getUserName())).withSelfRel();
        this.add(selfLink);
    }

    public MemberResource(String teamId, String memberName) throws Exception {
        Link selfLink = linkTo(methodOn(TeamMembersController.class).getMember(teamId, memberName)).withSelfRel();
        this.add(selfLink);

        Link teamLink = linkTo(methodOn(TeamsController.class).findByUserName(memberName)).withRel("teams");
        this.add(teamLink);
    }
}
