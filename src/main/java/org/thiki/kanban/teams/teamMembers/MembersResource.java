package org.thiki.kanban.teams.teamMembers;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.teams.invitation.InvitationController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by xubt on 9/10/16.
 */
@Service
public class MembersResource extends RestResource {
    @Resource
    private TLink tlink;

    @Resource
    private MemberResource memberResourceService;

    public Object toResource(String teamId, List<Member> members, String userName) throws Exception {
        MembersResource membersResource = new MembersResource();
        List<Object> memberResources = new ArrayList<>();
        for (Member member : members) {
            Object memberResource = memberResourceService.toResource(teamId, member, userName);
            memberResources.add(memberResource);
        }

        membersResource.buildDataObject("members", memberResources);

        Link invitationLink = linkTo(methodOn(InvitationController.class).invite(null, teamId, userName)).withRel("invitation");
        membersResource.add(tlink.from(invitationLink).build(userName));

        Link memberLink = linkTo(methodOn(TeamMembersController.class).getMember(teamId, userName)).withRel("member");
        membersResource.add(tlink.from(memberLink).build(userName));

        return membersResource.getResource();
    }
}
