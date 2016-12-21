package org.thiki.kanban.teams.team;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.RestResource;
import org.thiki.kanban.foundation.hateoas.TLink;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubt on 9/9/16.
 */
@Service
public class TeamsResource extends RestResource {
    @Resource
    private TLink tlink;
    @Resource
    private TeamResource teamResourceService;

    @Cacheable(value = "team", key = "'resource-teams'+#userName")
    public Object toResource(String userName, List<Team> teamList) throws Exception {
        TeamsResource teamsResource = new TeamsResource();
        teamsResource.domainObject = teamList;

        List<Object> teamsResources = new ArrayList<>();
        for (Team team : teamList) {
            Object teamResource = teamResourceService.toResource(userName, team);
            teamsResources.add(teamResource);
        }
        teamsResource.buildDataObject("teams", teamsResources);
        return teamsResource.getResource();
    }
}
