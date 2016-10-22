package org.thiki.kanban.teams.authentication;

/**
 * Created by xubt on 21/10/2016.
 */
public interface Authentication {
    boolean authenticate(String url, String userName);

    void config(String hrefValue, String userName);

    boolean get();

    boolean post();

    boolean delete();

    boolean put();

    String getPath();

    boolean matchPath(String path);
}
