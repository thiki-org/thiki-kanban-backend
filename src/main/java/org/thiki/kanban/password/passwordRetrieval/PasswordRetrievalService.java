package org.thiki.kanban.password.passwordRetrieval;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.VerificationCodeService;
import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.mail.MailService;
import org.thiki.kanban.foundation.security.md5.MD5Service;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.password.password.PasswordCodes;
import org.thiki.kanban.password.password.PasswordPersistence;
import org.thiki.kanban.password.passwordReset.PasswordReset;
import org.thiki.kanban.password.passwordReset.PasswordResetApplication;
import org.thiki.kanban.registration.Registration;
import org.thiki.kanban.registration.RegistrationPersistence;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;

/**
 * Created by xubt on 8/8/16.
 */
@Service
public class PasswordRetrievalService {

    private static final String passwordRetrievalEmailTemplate = "passwordRetrieval.ftl";
    @Resource
    private PasswordPersistence passwordPersistence;
    @Resource
    private VerificationCodeService verificationCodeService;
    @Resource
    private RegistrationPersistence registrationPersistence;
    @Resource
    private RSAService rsaService;

    @Resource
    private DateService dateService;

    @Resource
    private MailService mailService;

    public void createPasswordRetrievalApplication(PasswordRetrievalApplication passwordRetrievalApplication) throws TemplateException, IOException, MessagingException {
        Registration registeredUser = registrationPersistence.findByEmail(passwordRetrievalApplication.getEmail());
        if (registeredUser == null) {
            throw new BusinessException(PasswordCodes.EMAIL_IS_NOT_EXISTS.code(), PasswordCodes.EMAIL_IS_NOT_EXISTS.message());
        }

        String verificationCode = verificationCodeService.generate();

        passwordRetrievalApplication.setVerificationCode(verificationCode);
        passwordPersistence.clearUnfinishedApplication(passwordRetrievalApplication);
        passwordPersistence.createPasswordRetrievalApplication(passwordRetrievalApplication);

        VerificationCodeEmailData verificationCodeEmailData = new VerificationCodeEmailData();
        verificationCodeEmailData.setReceiver(registeredUser.getEmail());
        verificationCodeEmailData.setUserName(registeredUser.getName());
        verificationCodeEmailData.setVerificationCode(verificationCode);
        mailService.sendMailByTemplate(verificationCodeEmailData, passwordRetrievalEmailTemplate);
    }

    public void createPasswordResetRecord(PasswordResetApplication passwordResetApplication) {
        PasswordRetrievalApplication passwordRetrievalApplication = passwordPersistence.verify(passwordResetApplication);
        if (passwordRetrievalApplication == null) {
            throw new BusinessException(PasswordCodes.NO_PASSWORD_RETRIEVAL_RECORD.code(), PasswordCodes.NO_PASSWORD_RETRIEVAL_RECORD.message());
        }
        if (!passwordResetApplication.getVerificationCode().equals(passwordRetrievalApplication.getVerificationCode())) {
            throw new BusinessException(PasswordCodes.SECURITY_CODE_IS_NOT_CORRECT.code(), PasswordCodes.SECURITY_CODE_IS_NOT_CORRECT.message());
        }
        Date expiredTime = dateService.addMinute(passwordRetrievalApplication.getModificationTime(), 5);
        if (expiredTime.before(dateService.now())) {
            throw new BusinessException(PasswordCodes.SECURITY_CODE_TIMEOUT.code(), PasswordCodes.SECURITY_CODE_TIMEOUT.message());
        }
        passwordPersistence.passSecurityCodeVerification(passwordResetApplication.getEmail());
        passwordPersistence.createPasswordResetApplication(passwordResetApplication);
    }

    public void resetPassword(PasswordReset passwordReset) throws Exception {
        PasswordReset passwordResetRecord = passwordPersistence.findPasswordResetByEmail(passwordReset.getEmail());
        if (passwordResetRecord == null) {
            throw new BusinessException(PasswordCodes.NO_PASSWORD_RESET_RECORD.code(), PasswordCodes.NO_PASSWORD_RESET_RECORD.message());
        }
        Registration registeredUser = registrationPersistence.findByEmail(passwordReset.getEmail());

        String dencryptPassword = rsaService.dencrypt(passwordReset.getPassword());
        dencryptPassword = MD5Service.encrypt(dencryptPassword + registeredUser.getSalt());
        if (passwordReset != null) {
            passwordReset.setPassword(dencryptPassword);
        }
        passwordPersistence.resetPassword(passwordReset);
        passwordPersistence.cleanResetPasswordRecord(passwordReset);
    }
}
