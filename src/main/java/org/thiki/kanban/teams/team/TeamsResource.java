package org.thiki.kanban.teams.team;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;

/**
 * Created by xubt on 9/9/16.
 */
public class TeamsResource extends RestResource {
    public TeamsResource(String userName, List<Team> teamList) throws Exception {
        this.domainObject = teamList;
        JSONArray teamsJSONArray = new JSONArray();
        for (Team team : teamList) {
            TeamResource teamResource = new TeamResource(userName, team);
            JSONObject teamJSON = teamResource.getResource();
            teamsJSONArray.add(teamJSON);
        }
        this.resourcesJSON = teamsJSONArray;
    }
}
