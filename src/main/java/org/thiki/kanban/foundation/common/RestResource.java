package org.thiki.kanban.foundation.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.test.util.ReflectionTestUtils;
import org.thiki.kanban.foundation.hateoas.TLink;

import java.util.List;

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
            links.put(link.getRel(), ((TLink) link).toJSON());
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

    public void buildDataObject(String key, Object value) {
        JSONArray domainResources;
        if (value instanceof List) {
            domainResources = new JSONArray();
            for (int i = 0; i < ((List) value).size(); i++) {
                JSONObject domainResource = ReflectionTestUtils.invokeMethod(((List) value).get(i), "getResource");
                domainResources.add(domainResource);
            }
            value = domainResources;
        }
        JSONObject dataObject = new JSONObject();
        dataObject.put(key, value);
        this.domainObject = dataObject;
    }
}
