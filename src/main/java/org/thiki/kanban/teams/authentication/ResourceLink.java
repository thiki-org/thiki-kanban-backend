package org.thiki.kanban.teams.authentication;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by xubt on 22/10/2016.
 */
public class ResourceLink {
    private final String linkName;
    private final String linkHref;
    private final String userName;
    private boolean isAllowedCreate = true;
    private boolean isAllowedModify = true;
    private boolean isAllowedDelete = true;
    private boolean isAllowedRead = true;

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

        JSONObject methodsAuthInformation = new JSONObject();
        JSONObject postMethod = new JSONObject();
        postMethod.put("isAllowed", isAllowedCreate);

        JSONObject getMethod = new JSONObject();
        getMethod.put("isAllowed", isAllowedRead);

        JSONObject putMethod = new JSONObject();
        putMethod.put("isAllowed", isAllowedModify);

        JSONObject deleteMethod = new JSONObject();
        deleteMethod.put("isAllowed", isAllowedDelete);

        methodsAuthInformation.put("create", postMethod);
        methodsAuthInformation.put("read", getMethod);
        methodsAuthInformation.put("modify", putMethod);
        methodsAuthInformation.put("delete", deleteMethod);
        authenticatedLink.put("methods", methodsAuthInformation);
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
