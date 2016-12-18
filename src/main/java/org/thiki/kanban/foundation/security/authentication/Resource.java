package org.thiki.kanban.foundation.security.authentication;

/**
 * Created by xubt on 17/12/2016.
 */
public class Resource {
    private String url;
    private String GET;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGET() {
        return GET;
    }

    public void setGET(String GET) {
        this.GET = GET;
    }
}
