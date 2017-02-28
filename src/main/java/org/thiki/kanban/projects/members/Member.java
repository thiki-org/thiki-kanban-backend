package org.thiki.kanban.projects.members;

import org.thiki.kanban.user.Profile;
import org.thiki.kanban.user.User;

/**
 * Created by xubt on 9/10/16.
 */
public class Member extends User {
    private Profile profile;
    private String projectId;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
