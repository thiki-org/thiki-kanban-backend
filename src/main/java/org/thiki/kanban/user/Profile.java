package org.thiki.kanban.user;

import com.alibaba.fastjson.JSON;

/**
 * Created by xubt on 28/09/2016.
 */
public class Profile {

    private String id;
    private String userName;
    private String avatar;
    private String nickName;
    private String email;

    public Profile(String userName) {
        this.userName = userName;
    }

    public Profile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserName() {
        return userName;
    }

    public String getNickName() {
        return nickName == null ? userName : nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
