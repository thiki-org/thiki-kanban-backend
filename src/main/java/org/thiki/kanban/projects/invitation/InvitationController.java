package org.thiki.kanban.projects.invitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;

/**
 * Created by bogehu on 7/11/16.
 */
@RestController
public class InvitationController {
    @Autowired
    private InvitationService invitationService;

    @Resource
    private InvitationResource invitationResource;

    @RequestMapping(value = "/projects/{projectId}/members/invitation", method = RequestMethod.POST)
    public HttpEntity invite(@RequestBody Invitation invitation, @PathVariable("projectId") String projectId, @RequestHeader("userName") String userName) throws Exception {
        Invitation savedInvitation = invitationService.invite(userName, projectId, invitation);
        return Response.post(invitationResource.toResource(userName, projectId, savedInvitation));
    }

    @RequestMapping(value = "/projects/{projectId}/members/invitation/{invitationId}", method = RequestMethod.PUT)
    public HttpEntity acceptInvitation(@PathVariable("projectId") String projectId, @PathVariable("invitationId") String invitationId, @RequestHeader("userName") String userName) throws Exception {
        Invitation invitation = invitationService.acceptInvitation(userName, projectId, invitationId);
        return Response.build(invitationResource.toResource(userName, projectId, invitation));
    }

    @RequestMapping(value = "/projects/{projectId}/members/invitation/{invitationId}", method = RequestMethod.GET)
    public HttpEntity loadInvitationById(@PathVariable("projectId") String projectId, @PathVariable("invitationId") String invitationId, @RequestHeader("userName") String userName) throws Exception {
        Invitation invitation = invitationService.loadInvitation(invitationId);
        return Response.build(invitationResource.toResource(userName, projectId, invitation));
    }
}
