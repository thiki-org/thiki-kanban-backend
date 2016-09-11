package org.thiki.kanban.teams.teamMembers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.util.List;

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

    @RequestMapping(value = "/teams/{teamId}/members", method = RequestMethod.GET)
    public HttpEntity loadMembersByTeamId(@PathVariable String teamId, @RequestHeader String userName) {
        List<Member> members = teamMembersService.loadMembersByTeamId(userName, teamId);
        return Response.build(new MembersResource(members));
    }
}
