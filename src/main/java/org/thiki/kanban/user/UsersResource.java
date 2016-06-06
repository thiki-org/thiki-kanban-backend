package org.thiki.kanban.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.common.RestResource;

import java.util.List;
public class UsersResource extends RestResource {
    public UsersResource(List<User> users) {
        this.domainObject = users;
        JSONArray usersJSONArray = new JSONArray();
        for (User user : users) {
            UserResource userResource = new UserResource(user);
            JSONObject userJSON = userResource.getResource();
            usersJSONArray.add(userJSON);
        }
        this.resourcesJSON = usersJSONArray;
    }
}
