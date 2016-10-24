package org.thiki.kanban.entrance.auth;

import org.springframework.web.util.UriTemplate;
import org.thiki.kanban.teams.authentication.Authentication;

import java.util.Map;

/**
 * Created by xubt on 24/10/2016.
 */
public abstract class AuthProvider implements Authentication {

    protected String userName;
    protected String hrefValue;

    @Override
    public boolean authenticate(String url, String method, String userName) {
        return true;
    }

    @Override
    public void config(String hrefValue, String userName) {
        this.hrefValue = hrefValue;
        this.userName = userName;
    }

    @Override
    public boolean authGet() {
        return false;
    }

    @Override
    public boolean authPost() {
        return false;
    }

    @Override
    public boolean authDelete() {
        return false;
    }

    @Override
    public boolean authPut() {
        return false;
    }

    public String getPathTemplate() {
        return null;
    }

    @Override
    public boolean matchPath(String url) {
        UriTemplate uriTemplate = new UriTemplate(getPathTemplate());
        return uriTemplate.matches(url);
    }

    @Override
    public Map getPathValues(String url) {
        UriTemplate uriTemplate = new UriTemplate(getPathTemplate());
        Map pathValues = uriTemplate.match(url);
        return pathValues;
    }
}
