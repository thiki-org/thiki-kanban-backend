package org.thiki.kanban.password.passwordReset;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.thiki.kanban.foundation.security.md5.MD5Service;

import javax.validation.constraints.NotNull;

/**
 * Created by xubt on 8/15/16.
 */
public class PasswordReset {
    @NotNull(message = "邮箱不能为空。")
    @Email(message = "邮箱格式不正确。")
    @Length(max = 40, message = "邮箱超出长度限制。")
    private String email;
    @NotNull(message = "密码不能为空。")
    @Length(max = 200, message = "密码超出长度限制。")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() throws Exception {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void encryptPassword(String slat, String dencryptPassword) throws Exception {
        this.password = MD5Service.encrypt(dencryptPassword + slat);
    }
}
