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
        JSONObject authenticatedLinks = new JSONObject();
        for (ResourceLink resourceLink : resourceLinks) {
            authenticatedLinks.put(resourceLink.getName(), resourceLink.auth());
        }
        return authenticatedLinks;
    }
}
