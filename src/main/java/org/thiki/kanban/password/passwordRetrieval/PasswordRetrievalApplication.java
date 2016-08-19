package org.thiki.kanban.password.passwordRetrieval;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.password.password.PasswordCodes;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by xubt on 8/14/16.
 */
public class PasswordRetrievalApplication {
    public static final int PERIOD = 5;

    private String id;
    @NotNull(message = "用于找回密码的邮箱不能为空.")
    @Email(message = "邮箱格式错误.")
    @Length(max = 40, message = "邮箱超出长度限制。")
    private String email;

    private String verificationCode;
    private Date creationTime;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void verifyVerificationCodeIsCorrect(String verificationCode) {
        if (!verificationCode.equals(this.getVerificationCode())) {
            throw new BusinessException(PasswordCodes.SECURITY_CODE_IS_NOT_CORRECT.code(), PasswordCodes.SECURITY_CODE_IS_NOT_CORRECT.message());
        }
    }

    public void verifyVerificationCodeIsNotExpired(Date creationTime) {
        DateService dateService = new DateService();
        Date expiredTime = dateService.addMinute(creationTime, PERIOD);
        if (expiredTime.before(dateService.now())) {
            throw new BusinessException(PasswordCodes.SECURITY_CODE_TIMEOUT.code(), PasswordCodes.SECURITY_CODE_TIMEOUT.message());
        }
    }
}
