package org.thiki.kanban.teams.authentication;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xubt on 22/10/2016.
 */
public class ResourceLinks {
    private List<ResourceLink> resourceLinks;

    public ResourceLinks(Map<String, Object> resourceLinks, String userName) {
        this.resourceLinks = new ArrayList<>();
        for (Map.Entry<String, Object> entryObject : resourceLinks.entrySet()) {
            JSONObject linkObject = (JSONObject) entryObject.getValue();
            ResourceLink resourceLink = new ResourceLink(entryObject.getKey(), linkObject.getString("href"), userName);
            this.resourceLinks.add(resourceLink);
        }
    }

    public JSONObject auth() {
        JSONObject jsonObject = new JSONObject();
        for (ResourceLink resourceLink : resourceLinks) {
            jsonObject.put(resourceLink.getName(), resourceLink.end());
        }
        return jsonObject;
    }
}
