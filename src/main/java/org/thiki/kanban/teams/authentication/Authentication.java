package org.thiki.kanban.teams.authentication;

import java.util.Map;

/**
 * Created by xubt on 21/10/2016.
 */
public interface Authentication {
    boolean authenticate(String url, String method, String userName);

    void config(String hrefValue, String userName);

    boolean authGet();

    boolean authPost();

    boolean authDelete();

    boolean authPut();

    String getPathTemplate();

    boolean matchPath(String path);

    Map getPathValues(String url);
}
