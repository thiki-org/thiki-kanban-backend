package org.thiki.kanban.registration;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

/**
 * Created by xubt on 7/7/16.
 */
public class Registration {

    private String id;
    @Email(message = "邮箱格式错误")
    private String email;
    @NotNull(message = "用户名不可以为空")
    private String name;
    @NotNull(message = "密码不可以为空")
    private String password;

    private String salt;
    private String creationTime;
    private String modificationTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
