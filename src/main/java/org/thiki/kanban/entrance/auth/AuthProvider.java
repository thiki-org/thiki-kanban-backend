package org.thiki.kanban.entrance.auth;

import org.springframework.web.util.UriTemplate;
import org.thiki.kanban.teams.authentication.Authentication;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xubt on 24/10/2016.
 */
public class AuthProvider implements Authentication {

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
        try {
            String path = new URL(url).getPath();
            UriTemplate uriTemplate = new UriTemplate(getPathTemplate());
            return uriTemplate.matches(path);
        } catch (MalformedURLException e) {
            throw new RuntimeException("提取Path出错。URL:" + url);
        }
    }
}
