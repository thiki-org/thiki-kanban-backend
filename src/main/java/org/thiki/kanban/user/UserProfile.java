package org.thiki.kanban.user;

import java.io.Serializable;

public class UserProfile implements Serializable {

    private String id;

    private String email;

    private String name;
    private String nick;
    private String phone;

    private String creationTime;
    private String modificationTime;

    /**
     * @param id               userId
     * @param email
     * @param name             唯一的标示名字
     * @param nick             用户昵称,可以重复
     * @param phone
     * @param creationTime
     * @param modificationTime
     */
    public UserProfile(String id, String email, String name, String nick, String phone, String creationTime, String modificationTime) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nick = nick;
        this.phone = phone;
        this.creationTime = creationTime;
        this.modificationTime = modificationTime;
    }

    public UserProfile() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
