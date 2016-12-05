package org.thiki.kanban.teams.invitation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

/**
 * Created by bogehu on 7/11/16.
 */
@RestController
public class InvitationController {
    @Autowired
    private InvitationService invitationService;

    @RequestMapping(value = "/teams/{teamId}/members/invitation", method = RequestMethod.POST)
    public HttpEntity invite(@RequestBody Invitation invitation, @PathVariable("teamId") String teamId, @RequestHeader("userName") String userName) throws Exception {
        Invitation savedInvitation = invitationService.invite(userName, teamId, invitation);
        return Response.post(new InvitationResource(userName, teamId, savedInvitation));
    }

    @RequestMapping(value = "/teams/{teamId}/members/invitation/{invitationId}", method = RequestMethod.PUT)
    public HttpEntity acceptInvitation(@PathVariable("teamId") String teamId, @PathVariable("invitationId") String invitationId, @RequestHeader("userName") String userName) throws Exception {
        Invitation invitation = invitationService.acceptInvitation(userName, teamId, invitationId);
        return Response.build(new InvitationResource(userName, teamId, invitation));
    }

    @RequestMapping(value = "/teams/{teamId}/members/invitation/{invitationId}", method = RequestMethod.GET)
    public HttpEntity loadInvitationById(@PathVariable("teamId") String teamId, @PathVariable("invitationId") String invitationId, @RequestHeader("userName") String userName) throws Exception {
        Invitation invitation = invitationService.loadInvitation(invitationId);
        return Response.build(new InvitationResource(userName, teamId, invitation));
    }
}
