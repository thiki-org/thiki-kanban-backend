package org.thiki.kanban.foundation.security.authentication;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriTemplate;
import org.thiki.kanban.foundation.hateoas.Action;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.springframework.util.ResourceUtils.isUrl;

/**
 * Created by xubt on 24/10/2016.
 */
public abstract class AuthenticationProvider implements Authentication {

    protected String userName;
    protected String hrefValue;
    protected Map<String, String> pathParams;
    @Value("${server.contextPath}")
    protected String contextPath;

    @Override
    public boolean authenticate(String url, String method, String userName) {
        if (method.equals(MethodType.GET)) {
            return authGet();
        }
        if (method.equals(MethodType.POST)) {
            return authPost();
        }
        if (method.equals(MethodType.DELETE)) {
            return authDelete();
        }

        return !method.equals(MethodType.PUT) || authPut();
    }

    @Override
    public void config(String hrefValue, String userName) {
        this.hrefValue = hrefValue;
        this.userName = userName;
        this.pathParams = getPathValues(hrefValue);
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
    public boolean matchPath(String path) {
        try {
            if (isUrl(path)) {
                path = new URL(path).getPath();
            }
            path = path.replace(contextPath, "");
            UriTemplate uriTemplate = new UriTemplate(getPathTemplate());
            return uriTemplate.matches(path);
        } catch (MalformedURLException e) {
            throw new RuntimeException("提取Path出错。URL_TEMPLATE:" + path);
        }
    }

    @Override
    public Map getPathValues(String url) {
        UriTemplate uriTemplate = new UriTemplate(getPathTemplate());
        Map pathValues = uriTemplate.match(url);
        return pathValues;
    }

    public List<Action> authenticate() {
        return null;
    }
}
