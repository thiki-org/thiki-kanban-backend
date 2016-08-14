package org.thiki.kanban.teamMembers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

/**
 * Created by 濤 on 7/26/16.
 */
@RestController
public class TeamMembersController {
    @Autowired
    private TeamMembersService teamMembersService;

    @RequestMapping(value = "/teams/{teamId}/teamMembers", method = RequestMethod.POST)
    public HttpEntity joinTeam(@RequestBody TeamMember teamMember, @PathVariable String teamId, @RequestHeader String userName) {
        TeamMember savedTeamMember = teamMembersService.joinTeam(teamId, teamMember, userName);
        return Response.post(new TeamMembersResource(teamId, savedTeamMember));
    }
}
