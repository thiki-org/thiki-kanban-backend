package org.thiki.kanban.projects.projectMembers;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.projects.project.ProjectsController;
import org.thiki.kanban.user.UsersController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by 濤 on 7/26/16.
 */
@Service
public class MemberResource extends RestResource {
    @Resource
    private TLink tlink;

    public Object toResource(String projectId, Member member, String userName) throws Exception {
        MemberResource memberResource = new MemberResource();
        memberResource.domainObject = member;
        Link profileLink = linkTo(methodOn(UsersController.class).loadProfile(member.getUserName())).withRel("profile");
        memberResource.add(tlink.from(profileLink).build(userName));

        Link avatarLink = linkTo(UsersController.class, UsersController.class.getMethod("loadAvatar", String.class, HttpServletResponse.class), userName).withRel("avatar");
        memberResource.add(tlink.from(avatarLink).build(userName));

        Link selfLink = linkTo(methodOn(ProjectMembersController.class).getMember(projectId, member.getUserName())).withSelfRel();
        memberResource.add(tlink.from(selfLink).build(userName));
        return memberResource.getResource();
    }

    public Object toResource(String projectId, String memberName, String userName) throws Exception {
        MemberResource memberResource = new MemberResource();
        Link selfLink = linkTo(methodOn(ProjectMembersController.class).getMember(projectId, memberName)).withSelfRel();
        memberResource.add(tlink.from(selfLink).build(userName));

        Link projectLink = linkTo(methodOn(ProjectsController.class).findByUserName(memberName)).withRel("projects");
        memberResource.add(tlink.from(projectLink).build(userName));
        return memberResource.getResource();
    }
}
