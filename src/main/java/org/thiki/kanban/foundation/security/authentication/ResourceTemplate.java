package org.thiki.kanban.foundation.security.authentication;

import org.springframework.web.util.UriTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.springframework.util.ResourceUtils.isUrl;

/**
 * Created by xubt on 17/12/2016.
 */

public class ResourceTemplate {
    private String template;
    private boolean GET = false;
    private boolean POST = false;
    private boolean PUT = false;
    private boolean DELETE = false;

    public boolean isGET() {
        return GET;
    }

    public void setGET(boolean GET) {
        this.GET = GET;
    }

    public boolean isPOST() {
        return POST;
    }

    public void setPOST(boolean POST) {
        this.POST = POST;
    }

    public boolean isPUT() {
        return PUT;
    }

    public void setPUT(boolean PUT) {
        this.PUT = PUT;
    }

    public boolean isDELETE() {
        return DELETE;
    }

    public void setDELETE(boolean DELETE) {
        this.DELETE = DELETE;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    boolean match(String url, String method, String contextPath) {
        try {
            if (isUrl(url)) {
                url = new URL(url).getPath();
            }
            url = url.replace(contextPath, "");
            UriTemplate uriTemplate = new UriTemplate(template);
            return uriTemplate.matches(url) && isMethodAllowed(method);
        } catch (MalformedURLException e) {
            throw new RuntimeException("提取Path出错。URL_TEMPLATE:" + url);
        }
    }

    public Map getPathValues(String url) {
        UriTemplate uriTemplate = new UriTemplate(template);
        return uriTemplate.match(url);
    }

    private boolean isMethodAllowed(String method) {
        if ("GET".equals(method)) {
            return GET;
        }

        if ("POST".equals(method)) {
            return POST;
        }
        if ("DELETE".equals(method)) {
            return DELETE;
        }
        return "PUT".equals(method) && PUT;
    }
}
