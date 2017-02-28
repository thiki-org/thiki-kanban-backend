package org.thiki.kanban.projects.members;

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
public class MembersController {
    @Autowired
    private MembersService membersService;

    @Resource
    private MembersResource membersResource;

    @Resource
    private MemberResource memberResource;

    @RequestMapping(value = "/projects/{projectId}/members", method = RequestMethod.POST)
    public HttpEntity joinTeam(@RequestBody Member member, @PathVariable String projectId, @RequestHeader String userName) throws Exception {
        Member savedMember = membersService.joinTeam(projectId, member, userName);
        return Response.post(memberResource.toResource(projectId, savedMember, userName));
    }

    @RequestMapping(value = "/projects/{projectId}/members", method = RequestMethod.GET)
    public HttpEntity loadMembersByProjectId(@PathVariable String projectId, @RequestHeader String userName) throws Exception {
        List<Member> members = membersService.loadMembersByTeamId(userName, projectId);
        return Response.build(membersResource.toResource(projectId, members, userName));
    }

    @RequestMapping(value = "/projects/{projectId}/members/{userName}", method = RequestMethod.GET)
    public HttpEntity getMember(@PathVariable String projectId, @PathVariable String userName) throws Exception {
        return null;
    }

    @RequestMapping(value = "/projects/{projectId}/members/{memberName}", method = RequestMethod.DELETE)
    public HttpEntity leaveTeam(@PathVariable String projectId, @PathVariable String memberName, @RequestHeader String userName) throws Exception {
        membersService.leaveTeam(projectId, memberName);
        return Response.build(memberResource.toResource(projectId, memberName, userName));
    }
}
