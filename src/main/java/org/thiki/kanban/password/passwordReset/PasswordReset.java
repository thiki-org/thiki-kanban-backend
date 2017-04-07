package org.thiki.kanban.password.passwordReset;

import org.hibernate.validator.constraints.Length;
import org.thiki.kanban.foundation.security.identification.md5.MD5Service;

import javax.validation.constraints.NotNull;

/**
 * Created by xubt on 8/15/16.
 */
public class PasswordReset {

    @NotNull(message = "密码不能为空。")
    @Length(max = 200, message = "密码超出长度限制。")
    private String password;
    private String oldPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String encryptPassword(String slat, String dencryptPassword) throws Exception {
        return MD5Service.encrypt(dencryptPassword + slat);
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
