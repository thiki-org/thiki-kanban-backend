package org.thiki.kanban.teams.authentication;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xubt on 22/10/2016.
 */
public class Tlinks {
    private List<Tlink> links;

    public Tlinks(Map<String, String> links, String userName) {
        this.links = new ArrayList<>();
        for (Map.Entry<String, String> entryObject : links.entrySet()) {
            Tlink tlink = new Tlink(entryObject.getValue(), entryObject.getValue(), userName);
            this.links.add(tlink);
        }
    }

    private JSONObject auth() {
        JSONObject jsonObject = new JSONObject();
        for (Tlink tlink : links) {
            jsonObject.put(tlink.getName(), tlink.end());
        }
        return jsonObject;
    }
}
