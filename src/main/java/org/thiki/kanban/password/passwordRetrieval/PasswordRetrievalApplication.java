package org.thiki.kanban.password.passwordRetrieval;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by xubt on 8/14/16.
 */
public class PasswordRetrievalApplication {
    private String id;
    @NotNull(message = "用于找回密码的邮箱不能为空.")
    @Email(message = "邮箱格式错误.")
    @Length(max = 40, message = "邮箱超出长度限制。")
    private String email;

    private String verificationCode;
    private Date modificationTime;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
