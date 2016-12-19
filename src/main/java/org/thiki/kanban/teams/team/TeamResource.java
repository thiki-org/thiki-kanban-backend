package org.thiki.kanban.teams.team;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;
import org.thiki.kanban.teams.teamMembers.TeamMembersController;

import javax.annotation.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by bogehu on 7/11/16.
 */
@Service
public class TeamResource extends RestResource {
    @Resource
    private TLink tlink;

    @Cacheable(value = "team", key = "'team'+#team.id+#userName")
    public Object toResource(String userName, Team team) throws Exception {
        TeamResource teamResource = new TeamResource();
        teamResource.domainObject = team;
        if (team != null) {
            Link selfLink = linkTo(methodOn(TeamsController.class).findById(team.getId(), userName)).withSelfRel();
            teamResource.add(tlink.from(selfLink).build(userName));

            Link membersLink = linkTo(methodOn(TeamMembersController.class).loadMembersByTeamId(team.getId(), userName)).withRel("members");
            teamResource.add(tlink.from(membersLink).build(userName));
        }
        return teamResource.getResource();
    }
}
