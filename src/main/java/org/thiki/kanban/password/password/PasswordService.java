package org.thiki.kanban.password.password;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.VerificationCodeService;
import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.mail.MailService;
import org.thiki.kanban.foundation.security.md5.MD5Service;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.password.passwordReset.PasswordReset;
import org.thiki.kanban.password.passwordReset.PasswordResetApplication;
import org.thiki.kanban.password.passwordRetrieval.PasswordRetrievalApplication;
import org.thiki.kanban.password.passwordRetrieval.VerificationCodeEmailData;
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
public class PasswordService {

    private static final String passwordRetrievalEmailTemplate = "passwordRetrieval.ftl";
    public static final int PERIOD = 5;
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
        verifyWhetherUserIsExists(registeredUser);

        String verificationCode = verificationCodeService.generate();

        passwordRetrievalApplication.setVerificationCode(verificationCode);

        passwordPersistence.clearUnfinishedApplication(passwordRetrievalApplication);
        passwordPersistence.createPasswordRetrievalApplication(passwordRetrievalApplication);

        sendVerificationCodeEmail(registeredUser, verificationCode);
    }

    public void createPasswordResetApplication(PasswordResetApplication passwordResetApplication) {
        PasswordRetrievalApplication passwordRetrievalApplication = passwordPersistence.loadRetrievalApplication(passwordResetApplication);

        verifyRetrievalApplicationIsNotNull(passwordRetrievalApplication);
        verifyVerificationCodeIsCorrect(passwordResetApplication, passwordRetrievalApplication);
        verifyVerificationCodeIsNotExpired(passwordRetrievalApplication);

        passwordPersistence.makeRetrievalApplicationPassed(passwordResetApplication.getEmail());
        passwordPersistence.createPasswordResetApplication(passwordResetApplication);
    }

    public void resetPassword(PasswordReset passwordReset) throws Exception {
        PasswordReset passwordResetRecord = passwordPersistence.loadResetApplicationByEmail(passwordReset.getEmail());
        if (passwordResetRecord == null) {
            throw new BusinessException(PasswordCodes.NO_PASSWORD_RESET_RECORD.code(), PasswordCodes.NO_PASSWORD_RESET_RECORD.message());
        }
        dencryptPassword(passwordReset);

        passwordPersistence.resetPassword(passwordReset);
        passwordPersistence.cleanResetApplication(passwordReset);
    }

    private void verifyWhetherUserIsExists(Registration registeredUser) {
        if (registeredUser == null) {
            throw new BusinessException(PasswordCodes.EMAIL_IS_NOT_EXISTS.code(), PasswordCodes.EMAIL_IS_NOT_EXISTS.message());
        }
    }

    private void dencryptPassword(PasswordReset passwordReset) throws Exception {
        Registration registeredUser = registrationPersistence.findByEmail(passwordReset.getEmail());

        String dencryptPassword = rsaService.dencrypt(passwordReset.getPassword());
        dencryptPassword = MD5Service.encrypt(dencryptPassword + registeredUser.getSalt());
        if (passwordReset != null) {
            passwordReset.setPassword(dencryptPassword);
        }
    }

    private void verifyVerificationCodeIsNotExpired(PasswordRetrievalApplication passwordRetrievalApplication) {
        Date expiredTime = dateService.addMinute(passwordRetrievalApplication.getModificationTime(), PERIOD);
        if (expiredTime.before(dateService.now())) {
            throw new BusinessException(PasswordCodes.SECURITY_CODE_TIMEOUT.code(), PasswordCodes.SECURITY_CODE_TIMEOUT.message());
        }
    }

    private void verifyVerificationCodeIsCorrect(PasswordResetApplication passwordResetApplication, PasswordRetrievalApplication passwordRetrievalApplication) {
        if (!passwordResetApplication.getVerificationCode().equals(passwordRetrievalApplication.getVerificationCode())) {
            throw new BusinessException(PasswordCodes.SECURITY_CODE_IS_NOT_CORRECT.code(), PasswordCodes.SECURITY_CODE_IS_NOT_CORRECT.message());
        }
    }

    private void verifyRetrievalApplicationIsNotNull(PasswordRetrievalApplication passwordRetrievalApplication) {
        if (passwordRetrievalApplication == null) {
            throw new BusinessException(PasswordCodes.NO_PASSWORD_RETRIEVAL_RECORD.code(), PasswordCodes.NO_PASSWORD_RETRIEVAL_RECORD.message());
        }
    }

    private void sendVerificationCodeEmail(Registration registeredUser, String verificationCode) throws TemplateException, IOException, MessagingException {
        VerificationCodeEmailData verificationCodeEmailData = new VerificationCodeEmailData();
        verificationCodeEmailData.setReceiver(registeredUser.getEmail());
        verificationCodeEmailData.setUserName(registeredUser.getName());
        verificationCodeEmailData.setVerificationCode(verificationCode);
        mailService.sendMailByTemplate(verificationCodeEmailData, passwordRetrievalEmailTemplate);
    }
}
