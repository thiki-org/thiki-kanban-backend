package org.thiki.kanban.foundation.hateoas;

import com.alibaba.fastjson.JSONObject;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;
import org.thiki.kanban.foundation.security.authentication.AuthenticationProvider;

import java.util.List;

/**
 * Created by xubt on 16/12/2016.
 */
@Service
public class TLink extends Link {
    private List<Action> actions;
    private ControllerLinkBuilder controllerLinkBuilder;

    public TLink appendActions(Link publicKeyLink) {
        TLink tlink = new TLink();
        ReflectionTestUtils.setField(tlink, "href", publicKeyLink.getHref());
        ReflectionTestUtils.setField(tlink, "rel", publicKeyLink.getRel());
        ReflectionTestUtils.setField(tlink, "template", ReflectionTestUtils.getField(publicKeyLink, "template"));
        return tlink;
    }

    public TLink withAuthenticationProvider(AuthenticationProvider authenticationProvider) {

        List<Action> actions = authenticationProvider.authenticate();
        this.actions = actions;
        return this;
    }

    public JSONObject toJSON() {
        JSONObject linkJSON = new JSONObject();
        linkJSON.put("href", this.getHref());
        JSONObject actions = new JSONObject();
        for (Action action : this.actions) {
            JSONObject actionJSON = new JSONObject();
            actionJSON.put("isAllowed", action.isAllowed());
            actions.put(action.getActionName(), actionJSON);
        }
        linkJSON.put("actions", actions);
        return linkJSON;
    }
}
