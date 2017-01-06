package org.thiki.kanban.foundation.security.authentication;

import java.util.Map;

/**
 * Created by xubt on 18/12/2016.
 */
public class MatchResult {
    private String roleName;
    private Map pathValues;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Map getPathValues() {
        return pathValues;
    }

    public void setPathValues(Map pathValues) {
        this.pathValues = pathValues;
    }
}
