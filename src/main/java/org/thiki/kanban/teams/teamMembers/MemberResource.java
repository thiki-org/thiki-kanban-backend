package org.thiki.kanban.teams.teamMembers;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.teams.team.TeamsController;
import org.thiki.kanban.user.UsersController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by 濤 on 7/26/16.
 */
@Service
public class MemberResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(String teamId, Member member, String userName) throws Exception {
        MemberResource memberResource = new MemberResource();
        memberResource.domainObject = member;
        Link profileLink = linkTo(methodOn(UsersController.class).loadProfile(member.getUserName())).withRel("profile");
        memberResource.add(tlink.from(profileLink).build(userName));

        Link avatarLink = linkTo(methodOn(UsersController.class).loadAvatar(member.getUserName())).withRel("avatar");
        memberResource.add(tlink.from(avatarLink).build(userName));

        Link selfLink = linkTo(methodOn(TeamMembersController.class).getMember(teamId, member.getUserName())).withSelfRel();
        memberResource.add(tlink.from(selfLink).build(userName));
        return memberResource.getResource();
    }

    public Object toResource(String teamId, String memberName, String userName) throws Exception {
        MemberResource memberResource = new MemberResource();
        Link selfLink = linkTo(methodOn(TeamMembersController.class).getMember(teamId, memberName)).withSelfRel();
        memberResource.add(tlink.from(selfLink).build(userName));

        Link teamLink = linkTo(methodOn(TeamsController.class).findByUserName(memberName)).withRel("teams");
        memberResource.add(tlink.from(teamLink).build(userName));
        return memberResource.getResource();
    }
}
