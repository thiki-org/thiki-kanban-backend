package org.thiki.kanban.passwordRetrieval;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.thiki.kanban.foundation.common.Response;
import org.thiki.kanban.team.Team;
import org.thiki.kanban.team.TeamResource;

import javax.annotation.Resource;

/**
 * Created by xubitao on 04/26/16.
 */
@RestController
public class PasswordRetrievalController {
    @Resource
    private PasswordRetrievalService passwordRetrievalService;

    @RequestMapping(value = "/passwordRetrievalApply", method = RequestMethod.POST)
    public HttpEntity passwordRetrieval(@RequestBody RegisterEmail registerEmail) throws Exception {
        Team savedTeam = passwordRetrievalService.createRetrievalRecord(registerEmail);
        return Response.post(new TeamResource(savedTeam));
    }
}
