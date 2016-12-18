package org.thiki.kanban.foundation.hateoas;

import com.alibaba.fastjson.JSONObject;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;
import org.springframework.test.util.ReflectionTestUtils;
import org.thiki.kanban.foundation.security.authentication.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xubt on 16/12/2016.
 */
@Service
public class TLink extends Link {
    @Resource
    private RolesResources rolesResources;

    @Resource
    private AuthenticationProviderFactory authenticationProviderFactory;
    private List<Action> actions;

    public TLink from(Link link) {
        TLink tlink = new TLink();
        tlink.setRolesResources(rolesResources);
        tlink.setAuthenticationProviderFactory(authenticationProviderFactory);
        ReflectionTestUtils.setField(tlink, "href", link.getHref());
        ReflectionTestUtils.setField(tlink, "rel", link.getRel());
        ReflectionTestUtils.setField(tlink, "template", ReflectionTestUtils.getField(link, "template"));
        return tlink;
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

    public Link build() {
        this.actions = new ArrayList<>();
        List<MethodType> methods = Arrays.asList(MethodType.GET, MethodType.POST, MethodType.DELETE, MethodType.PUT);
        for (MethodType methodType : methods) {
            MatchResult matchResult = rolesResources.match(this.getHref(), methodType.name());
            Authentication authenticationProvider = authenticationProviderFactory.loadProviderByRole(matchResult.getRoleName());
            if (null != authenticationProvider) {
                Action action = new Action();
                action.setActionName(methodType.name());
                action.setAllowed(true);
                this.actions.add(action);
            }
        }
        return this;
    }

    public void setRolesResources(RolesResources rolesResources) {
        this.rolesResources = rolesResources;
    }

    public void setAuthenticationProviderFactory(AuthenticationProviderFactory authenticationProviderFactory) {
        this.authenticationProviderFactory = authenticationProviderFactory;
    }

    public Link build(String userName) {
        this.actions = new ArrayList<>();
        List<MethodType> methods = Arrays.asList(MethodType.GET, MethodType.POST, MethodType.DELETE, MethodType.PUT);
        for (MethodType methodType : methods) {
            MatchResult matchResult = rolesResources.match(this.getHref(), methodType.name());
            Authentication authenticationProvider = authenticationProviderFactory.loadProviderByRole(matchResult.getRoleName());
            if (null != authenticationProvider) {
                Action action = new Action();
                action.setActionName(methodType.name());
                action.setAllowed(true);
                this.actions.add(action);
            }
        }
        return this;
    }
}
