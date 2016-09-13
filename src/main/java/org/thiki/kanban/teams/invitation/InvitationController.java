package org.thiki.kanban.teams.invitation;

import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Created by bogehu on 7/11/16.
 */
@RestController
public class InvitationController {
    @Autowired
    private InvitationService invitationService;

    @RequestMapping(value = "teams/{teamId}/members/invitation", method = RequestMethod.POST)
    public HttpEntity invite(@RequestBody Invitation invitation, @PathVariable("teamId") String teamId, @RequestHeader("userName") String userName) throws TemplateException, IOException, MessagingException {
        Invitation savedInvitation = invitationService.invite(userName, teamId, invitation);
        return Response.post(new InvitationResource(userName, teamId, savedInvitation));
    }

    @RequestMapping(value = "teams/{teamId}/members/invitation", method = RequestMethod.PUT)
    public HttpEntity acceptInvitation(@PathVariable("teamId") String teamId, @RequestParam("invitationId") String invitationId) {
        return null;
    }
}
