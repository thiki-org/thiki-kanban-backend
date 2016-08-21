package org.thiki.kanban.password.passwordReset;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Created by xubt on 8/15/16.
 */
public class PasswordResetApplication {
    private String id;
    private String userName;

    @NotNull(message = "验证码不能为空。")
    @Length(min = 6, max = 6, message = "验证码格式错误,应为6为数字。")
    private String verificationCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
