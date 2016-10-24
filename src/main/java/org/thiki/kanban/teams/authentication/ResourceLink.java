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

    private void auth() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return;
        }
        authentication.config(linkHref, userName);
        isAllowedRead = authentication.get();
        isAllowedCreate = authentication.post();
        isAllowedDelete = authentication.delete();
        isAllowedModify = authentication.put();
    }

    public JSONObject end() {
        auth();
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

        methodsAuthInformation.put("post", postMethod);
        methodsAuthInformation.put("get", getMethod);
        methodsAuthInformation.put("put", putMethod);
        methodsAuthInformation.put("delete", deleteMethod);
        authenticatedLink.put("methods", methodsAuthInformation);
        return authenticatedLink;
    }

    private String toPath() {
        return "/boards/feeId/procedures";
    }

    private Authentication getAuthentication() {
        Map<String, Authentication> authProviders = ApplicationContextProvider.getApplicationContext().getBeansOfType(Authentication.class);
        for (Map.Entry entry : authProviders.entrySet()) {
            Authentication auth = (Authentication) entry.getValue();
            if (auth.matchPath(toPath())) {
                return auth;
            }
        }
        return null;
    }


    public String getName() {
        return linkName;
    }
}
