package org.thiki.kanban.user.profile;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder AVATAR_FILES_LOCATION for storing files
     */
    private String location = "uploadFiles/avatars";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
