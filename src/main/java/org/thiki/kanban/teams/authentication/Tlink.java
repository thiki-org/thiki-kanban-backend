package org.thiki.kanban.teams.authentication;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created by xubt on 22/10/2016.
 */
public class Tlink {
    private final String hrefName;
    private final String hrefValue;
    private final String userName;
    private boolean isAllowedCreate = true;
    private boolean isAllowedModify = true;
    private boolean isAllowedDelete = true;
    private boolean isAllowedRead = true;

    public Tlink(String hrefName, String hrefValue, String userName) {
        this.hrefName = hrefName;
        this.hrefValue = hrefValue;
        this.userName = userName;
    }


    private void auth() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return;
        }
        authentication.config(hrefValue, userName);
        isAllowedCreate = authentication.get();
        isAllowedCreate = authentication.post();
        isAllowedCreate = authentication.delete();
        isAllowedCreate = authentication.put();
    }

    public JSONObject end() {
        auth();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(hrefName, hrefValue);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("post", true);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("get", true);

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("delete", true);

        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("put", true);
        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        jsonArray.add(jsonObject4);
        jsonObject.put("methods", jsonArray);
        return jsonObject;
    }

    private String toPath() {
        return "/boards/feeId/procedures";
    }

    private Authentication getAuthentication() {
        Map<String, Authentication> authProvidersauthProviders = ApplicationContextProvider.getApplicationContext().getBeansOfType(Authentication.class);
        for (Map.Entry entry : authProvidersauthProviders.entrySet()) {
            Authentication auth = (Authentication) entry.getValue();
            if (auth.matchPath(toPath())) {
                return auth;
            }
        }
        return null;
    }


    public String getName() {
        return hrefName;
    }
}
