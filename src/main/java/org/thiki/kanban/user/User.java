package org.thiki.kanban.user;

import com.alibaba.fastjson.JSON;

/**
 * Created by xubt on 7/7/16.
 */
public class User {
    private String id;
    private String email;
    private String userName;
    private String password;
    private String author;
    private String creationTime;
    private String modificationTime;
    private String salt;
    private Profile profile;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(String modificationTime) {
        this.modificationTime = modificationTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Profile getProfile() {
        if (this.profile == null) {
            this.profile = new Profile();
            this.profile.setNickName(this.userName);
            this.profile.setEmail(this.email);
        }
        return this.profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
