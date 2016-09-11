package org.thiki.kanban.teams.team;

import org.springframework.hateoas.Link;
import org.thiki.kanban.board.BoardsController;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.teams.teamMembers.TeamMembersController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by bogehu on 7/11/16.
 */
public class TeamResource extends RestResource {
    public TeamResource(String userName, Team team) {
        this.domainObject = team;
        if (team != null) {
            Link selfLink = linkTo(methodOn(TeamsController.class).findById(team.getId(), userName)).withSelfRel();
            this.add(selfLink);

            Link membersLink = linkTo(methodOn(TeamMembersController.class).loadMembersByTeamId(team.getId(), userName)).withRel("members");
            this.add(membersLink);

            Link boardsLink = linkTo(methodOn(BoardsController.class).findByTeamId(team.getId())).withRel("boards");
            this.add(boardsLink);
        }
    }
}
