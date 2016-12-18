package org.thiki.kanban.foundation.security.authentication;

import java.util.List;

/**
 * Created by xubt on 17/12/2016.
 */
public class Role {
    private String name;
    private List<ResourceTemplate> resources;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ResourceTemplate> getResources() {
        return resources;
    }

    public void setResources(List<ResourceTemplate> resources) {
        this.resources = resources;
    }
}

