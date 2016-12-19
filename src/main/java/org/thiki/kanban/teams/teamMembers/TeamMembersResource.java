package org.thiki.kanban.teams.teamMembers;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by 濤 on 7/26/16.
 */
@Service
public class TeamMembersResource extends RestResource {
    @Resource
    private TLink tlink;

    @Cacheable(value = "team", key = "'teamMember'+teamMember.id+#teamId+#userName")
    public Object toResource(String teamId, TeamMember teamMember, String userName) {
        TeamMembersResource teamMembersResource = new TeamMembersResource();
        teamMembersResource.domainObject = teamMember;
        if (teamMember != null) {
            Link selfLink = linkTo(methodOn(TeamMembersController.class).joinTeam(teamMember, teamId, null)).withSelfRel();
            teamMembersResource.add(tlink.from(selfLink).build(userName));
        }
        return teamMembersResource.getResource();
    }
}
