package org.thiki.kanban.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;
public class UsersResource extends RestResource {
    public UsersResource(List<UserProfile> userProfiles) {
        this.domainObject = userProfiles;
        JSONArray usersJSONArray = new JSONArray();
        for (UserProfile userProfile : userProfiles) {
            UserResource userResource = new UserResource(userProfile);
            JSONObject userJSON = userResource.getResource();
            usersJSONArray.add(userJSON);
        }
        this.resourcesJSON = usersJSONArray;
    }
}
