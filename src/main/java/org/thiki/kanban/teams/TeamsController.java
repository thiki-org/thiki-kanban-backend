package org.thiki.kanban.teams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import java.util.List;

/**
 * Created by bogehu on 7/11/16.
 */
@RestController
public class TeamsController {
    @Autowired
    private TeamsService teamsService;

    @RequestMapping(value = "/{userName}/teams", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Team team, @PathVariable("userName") String userName) {
        Team savedTeam = teamsService.create(userName, team);
        return Response.post(new TeamResource(userName, savedTeam));
    }

    @RequestMapping(value = "/{userName}/teams", method = RequestMethod.GET)
    public HttpEntity findByUserName(@PathVariable("userName") String userName) {

        List<Team> teamList = teamsService.findByUserName(userName);
        return Response.build(new TeamsResource(userName, teamList));
    }

    @RequestMapping(value = "/teams/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id, @RequestHeader("userName") String userName) {
        Team team = teamsService.findById(id);
        return Response.build(new TeamResource(userName, team));
    }

    @RequestMapping(value = "/{userName}/teams/{id}", method = RequestMethod.PUT)
    public HttpEntity update(@RequestBody Team team, @PathVariable String id, @PathVariable("userName") String userName) {
        team.setId(id);
        Team updatedTeam = teamsService.update(team);
        return Response.build(new TeamResource(userName, updatedTeam));
    }
}
