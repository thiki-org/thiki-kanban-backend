package org.thiki.kanban.user.registration;

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
    private String userName;
    @NotNull(message = "密码不可以为空")
    private String password;

    private String salt;

    public String getId() {
        return id;
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
}
