package org.thiki.kanban.team;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;

/**
 * Created by bogehu on 7/11/16.
 */
public class TeamsResource extends RestResource {
    public TeamsResource(List<Team> teams) {
        this.domainObject = teams;
        JSONArray teamsJSONArray = new JSONArray();
        for (Team team : teams) {
            TeamResource teamResource = new TeamResource(team);
            JSONObject teamJSON = teamResource.getResource();
            teamsJSONArray.add(teamJSON);
        }
        this.resourcesJSON = teamsJSONArray;
    }
}
