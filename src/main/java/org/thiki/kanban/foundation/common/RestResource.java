package org.thiki.kanban.foundation.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by xubt on 5/26/16.
 */
public class RestResource extends ResourceSupport {
    protected Object domainObject;
    protected JSONArray resourcesJSON;

    public JSONObject getResource() {
        JSONObject resourceJSON = new JSONObject();
        JSONObject links = new JSONObject();
        for (Link link : super.getLinks()) {
            JSONObject linkJSON = new JSONObject();
            linkJSON.put("href", link.getHref());
            links.put(link.getRel(), linkJSON);
        }
        JSONObject domainJSON = JSONObject.parseObject(JSONObject.toJSONString(domainObject));
        if (domainJSON != null) {
            resourceJSON.putAll(domainJSON);
        }
        resourceJSON.put("_links", links);
        return resourceJSON;
    }

    @Override
    public String toString() {
        if (resourcesJSON != null) {
            return resourcesJSON.toJSONString();
        }
        return getResource().toString();
    }

    public JSONArray getResources() {
        return resourcesJSON;
    }
}
