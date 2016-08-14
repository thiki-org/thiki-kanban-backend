package org.thiki.kanban.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

/**
 * Created by bogehu on 7/11/16.
 */
@RestController
public class TeamsController {
    @Autowired
    private TeamsService teamsService;

    @RequestMapping(value = "/teams", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Team team, @RequestHeader String userName) {
        Team savedTeam = teamsService.create(userName, team);
        return Response.post(new TeamResource(savedTeam));
    }

    @RequestMapping(value = "/teams/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id) {
        Team team = teamsService.findById(id);
        return Response.build(new TeamResource(team));
    }

    @RequestMapping(value = "/teams/{id}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Team team, @PathVariable String id) {
        team.setId(id);
        Team updatedTeam = teamsService.update(team);
        return Response.build(new TeamResource(updatedTeam));

    }
}
