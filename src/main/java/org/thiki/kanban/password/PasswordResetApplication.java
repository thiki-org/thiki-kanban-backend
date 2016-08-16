package org.thiki.kanban.password;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by xubt on 8/15/16.
 */
public class PasswordResetApplication {
    private String id;
    @NotNull(message = "邮箱不能为空。")
    @Email(message = "邮箱格式不正确。")
    @Length(max = 40, message = "邮箱超出长度限制。")
    private String email;

    @NotNull(message = "验证码不能为空。")
    @Length(min = 6, max = 6, message = "验证码格式错误,应为6为数字。")
    private String verificationCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getId() {
        return id;
    }
}
