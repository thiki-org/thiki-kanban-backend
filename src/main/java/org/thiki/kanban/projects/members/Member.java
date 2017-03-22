package org.thiki.kanban.projects.members;

import org.thiki.kanban.user.User;

/**
 * Created by xubt on 9/10/16.
 */
public class Member extends User {
    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
