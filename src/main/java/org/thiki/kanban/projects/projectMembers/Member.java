package org.thiki.kanban.projects.projectMembers;

import org.thiki.kanban.user.Profile;

/**
 * Created by xubt on 9/10/16.
 */
public class Member {
    private String userName;
    private String email;
    private Profile profile;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
