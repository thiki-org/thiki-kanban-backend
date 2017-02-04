package org.thiki.kanban.projects.projectMembers;

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
public class ProjectMembersController {
    @Autowired
    private ProjectMembersService projectMembersService;

    @Resource
    private ProjectMembersResource projectMembersResource;
    @Resource
    private MembersResource membersResource;

    @Resource
    private MemberResource memberResource;

    @RequestMapping(value = "/projects/{projectId}/members", method = RequestMethod.POST)
    public HttpEntity joinTeam(@RequestBody ProjectMember projectMember, @PathVariable String projectId, @RequestHeader String userName) {
        ProjectMember savedProjectMember = projectMembersService.joinTeam(projectId, projectMember, userName);
        return Response.post(projectMembersResource.toResource(projectId, savedProjectMember, userName));
    }

    @RequestMapping(value = "/projects/{projectId}/members", method = RequestMethod.GET)
    public HttpEntity loadMembersByTeamId(@PathVariable String projectId, @RequestHeader String userName) throws Exception {
        List<Member> members = projectMembersService.loadMembersByTeamId(userName, projectId);
        return Response.build(membersResource.toResource(projectId, members, userName));
    }

    @RequestMapping(value = "/projects/{projectId}/members/{userName}", method = RequestMethod.GET)
    public HttpEntity getMember(@PathVariable String projectId, @PathVariable String userName) throws Exception {
        return null;
    }

    @RequestMapping(value = "/projects/{projectId}/members/{memberName}", method = RequestMethod.DELETE)
    public HttpEntity leaveTeam(@PathVariable String projectId, @PathVariable String memberName, @RequestHeader String userName) throws Exception {
        projectMembersService.leaveTeam(projectId, memberName);
        return Response.build(memberResource.toResource(projectId, memberName, userName));
    }
}
