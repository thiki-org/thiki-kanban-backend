package org.thiki.kanban.teams.team;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.TemplateException;
import org.thiki.kanban.foundation.common.RestResource;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

/**
 * Created by xubt on 9/9/16.
 */
public class TeamsResource extends RestResource {
    public TeamsResource(String userName, List<Team> teamList) throws TemplateException, IOException, MessagingException {
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
