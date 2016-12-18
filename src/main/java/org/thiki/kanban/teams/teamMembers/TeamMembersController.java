package org.thiki.kanban.teams.teamMembers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 濤 on 7/26/16.
 */
@RestController
public class TeamMembersController {
    @Autowired
    private TeamMembersService teamMembersService;

    @Resource
    private TeamMembersResource teamMembersResource;
    @Resource
    private MembersResource membersResource;

    @Resource
    private MemberResource memberResource;

    @RequestMapping(value = "/teams/{teamId}/members", method = RequestMethod.POST)
    public HttpEntity joinTeam(@RequestBody TeamMember teamMember, @PathVariable String teamId, @RequestHeader String userName) {
        TeamMember savedTeamMember = teamMembersService.joinTeam(teamId, teamMember, userName);
        return Response.post(teamMembersResource.toResource(teamId, savedTeamMember, userName));
    }

    @RequestMapping(value = "/teams/{teamId}/members", method = RequestMethod.GET)
    public HttpEntity loadMembersByTeamId(@PathVariable String teamId, @RequestHeader String userName) throws Exception {
        List<Member> members = teamMembersService.loadMembersByTeamId(userName, teamId);
        return Response.build(membersResource.toResource(teamId, members, userName));
    }

    @RequestMapping(value = "/teams/{teamId}/members/{userName}", method = RequestMethod.GET)
    public HttpEntity getMember(@PathVariable String teamId, @PathVariable String userName) throws Exception {
        return null;
    }

    @RequestMapping(value = "/teams/{teamId}/members/{memberName}", method = RequestMethod.DELETE)
    public HttpEntity leaveTeam(@PathVariable String teamId, @PathVariable String memberName, @RequestHeader String userName) throws Exception {
        teamMembersService.leaveTeam(teamId, memberName);
        return Response.build(memberResource.toResource(teamId, memberName, userName));
    }
}
