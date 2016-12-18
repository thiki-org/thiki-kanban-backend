package org.thiki.kanban.teams.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.thiki.kanban.foundation.common.Response;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by bogehu on 7/11/16.
 */
@RestController
public class TeamsController {
    @Autowired
    private TeamsService teamsService;
    @Resource
    private TeamResource teamResource;
    @Resource
    private TeamsResource teamsResource;

    @RequestMapping(value = "/{userName}/teams", method = RequestMethod.POST)
    public HttpEntity create(@RequestBody Team team, @PathVariable("userName") String userName) throws Exception {
        Team savedTeam = teamsService.create(userName, team);
        return Response.post(teamResource.toResource(userName, savedTeam));
    }


    @RequestMapping(value = "/teams/{teamId}", method = RequestMethod.PUT)
    public HttpEntity updateTeam(@RequestBody Team team, @PathVariable("teamId") String teamId, @RequestHeader("userName") String userName) throws Exception {
        Team savedTeam = teamsService.update(teamId, team, userName);
        return Response.build(teamResource.toResource(userName, savedTeam));
    }

    @RequestMapping(value = "/{userName}/teams", method = RequestMethod.GET)
    public HttpEntity findByUserName(@PathVariable("userName") String userName) throws Exception {
        List<Team> teamList = teamsService.findByUserName(userName);
        return Response.build(teamsResource.toResource(userName, teamList));
    }

    @RequestMapping(value = "/teams/{id}", method = RequestMethod.GET)
    public HttpEntity findById(@PathVariable String id, @RequestHeader("userName") String userName) throws Exception {
        Team team = teamsService.findById(id);
        return Response.build(teamResource.toResource(userName, team));
    }
}
