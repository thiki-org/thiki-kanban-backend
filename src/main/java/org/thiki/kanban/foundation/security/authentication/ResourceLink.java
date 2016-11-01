package org.thiki.kanban.foundation.security.authentication;

import com.alibaba.fastjson.JSONObject;
import org.thiki.kanban.foundation.configuration.ApplicationContextProvider;

import java.util.Map;

/**
 * Created by xubt on 22/10/2016.
 */
public class ResourceLink {
    private final String linkName;
    private final String linkHref;
    private final String userName;
    private boolean isAllowedCreate;
    private boolean isAllowedModify;
    private boolean isAllowedDelete;
    private boolean isAllowedRead;

    public ResourceLink(String linkName, String linkHref, String userName) {
        this.linkName = linkName;
        this.linkHref = linkHref;
        this.userName = userName;
    }

    public JSONObject auth() {
        loadAuthenticationFormAuthProvider();
        return rebuildLinkWithAuthenticationInformation();
    }

    private void loadAuthenticationFormAuthProvider() {
        Authentication authentication = getAuthenticationProvider();
        if (authentication == null) {
            return;
        }
        authentication.config(linkHref, userName);
        isAllowedRead = authentication.authGet();
        isAllowedCreate = authentication.authPost();
        isAllowedDelete = authentication.authDelete();
        isAllowedModify = authentication.authPut();
    }

    private JSONObject rebuildLinkWithAuthenticationInformation() {
        JSONObject authenticatedLink = new JSONObject();
        authenticatedLink.put("href", linkHref);

        JSONObject ActionsAuthInformation = new JSONObject();
        if (isAllowedCreate) {
            JSONObject postAction = new JSONObject();
            postAction.put("isAllowed", isAllowedCreate);
            ActionsAuthInformation.put("create", postAction);
        }
        if (isAllowedRead) {
            JSONObject getAction = new JSONObject();
            getAction.put("isAllowed", isAllowedRead);
            ActionsAuthInformation.put("read", getAction);
        }
        if (isAllowedModify) {
            JSONObject putAction = new JSONObject();
            putAction.put("isAllowed", isAllowedModify);
            ActionsAuthInformation.put("modify", putAction);
        }
        if (isAllowedDelete) {
            JSONObject deleteAction = new JSONObject();
            deleteAction.put("isAllowed", isAllowedDelete);
            ActionsAuthInformation.put("delete", deleteAction);
        }
        authenticatedLink.put("actions", ActionsAuthInformation);
        return authenticatedLink;
    }

    private Authentication getAuthenticationProvider() {
        Map<String, Authentication> authProviders = ApplicationContextProvider.getApplicationContext().getBeansOfType(Authentication.class);
        for (Map.Entry entry : authProviders.entrySet()) {
            Authentication authProvider = (Authentication) entry.getValue();
            if (authProvider.getClass().getName().indexOf("org.thiki") > -1 && authProvider.matchPath(linkHref)) {
                return authProvider;
            }
        }
        return null;
    }

    public String getName() {
        return linkName;
    }
}
