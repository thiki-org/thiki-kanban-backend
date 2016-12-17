package org.thiki.kanban.foundation.hateoas;

import com.alibaba.fastjson.JSONObject;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

/**
 * Created by xubt on 16/12/2016.
 */
@Service
public class TLink extends Link {
    private List<Action> actions;

    public TLink from(Link link) {
        TLink tlink = new TLink();
        ReflectionTestUtils.setField(tlink, "href", link.getHref());
        ReflectionTestUtils.setField(tlink, "rel", link.getRel());
        ReflectionTestUtils.setField(tlink, "template", ReflectionTestUtils.getField(link, "template"));
        return tlink;
    }

    public TLink withActions(List<Action> actions) {
        this.actions = actions;
        return this;
    }

    public JSONObject toJSON() {
        JSONObject linkJSON = new JSONObject();
        linkJSON.put("href", this.getHref());
        JSONObject actions = new JSONObject();
        if (this.actions != null) {
            for (Action action : this.actions) {
                JSONObject actionJSON = new JSONObject();
                actionJSON.put("isAllowed", action.isAllowed());
                actions.put(action.getActionName(), actionJSON);
            }
            linkJSON.put("actions", actions);
        }
        return linkJSON;
    }
}
