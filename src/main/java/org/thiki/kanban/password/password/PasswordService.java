package org.thiki.kanban.password.password;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.VerificationCodeService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.mail.MailService;
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

/**
 * Created by xubt on 8/8/16.
 */
@Service
public class PasswordService {

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
    private MailService mailService;

    public void applyRetrieval(PasswordRetrievalApplication passwordRetrievalApplication) throws TemplateException, IOException, MessagingException {
        Registration registeredUser = registrationPersistence.findByEmail(passwordRetrievalApplication.getEmail());
        verifyWhetherUserIsExists(registeredUser);

        String verificationCode = verificationCodeService.generate();

        passwordRetrievalApplication.setVerificationCode(verificationCode);

        passwordPersistence.clearUnfinishedApplication(passwordRetrievalApplication);
        passwordPersistence.createPasswordRetrievalApplication(passwordRetrievalApplication);

        sendVerificationCodeEmail(registeredUser, verificationCode);
    }

    public void applyReset(PasswordResetApplication passwordResetApplication) {
        PasswordRetrievalApplication passwordRetrievalApplication = passwordPersistence.loadRetrievalApplication(passwordResetApplication);

        verifyRetrievalApplicationIsNotNull(passwordRetrievalApplication);
        passwordRetrievalApplication.verifyVerificationCodeIsCorrect(passwordResetApplication.getVerificationCode());
        passwordRetrievalApplication.verifyVerificationCodeIsNotExpired(passwordRetrievalApplication.getCreationTime());

        passwordPersistence.makeRetrievalApplicationPassed(passwordResetApplication.getEmail());
        passwordPersistence.createPasswordResetApplication(passwordResetApplication);
    }

    public void resetPassword(PasswordReset passwordReset) throws Exception {
        PasswordReset passwordResetRecord = passwordPersistence.loadResetApplicationByEmail(passwordReset.getEmail());
        if (passwordResetRecord == null) {
            throw new BusinessException(PasswordCodes.NO_PASSWORD_RESET_RECORD.code(), PasswordCodes.NO_PASSWORD_RESET_RECORD.message());
        }

        Registration registeredUser = registrationPersistence.findByEmail(passwordReset.getEmail());
        String dencryptPassword = rsaService.dencrypt(passwordReset.getPassword());

        passwordReset.encryptPassword(registeredUser.getSalt(), dencryptPassword);

        passwordPersistence.resetPassword(passwordReset);
        passwordPersistence.cleanResetApplication(passwordReset);
    }

    private void verifyWhetherUserIsExists(Registration registeredUser) {
        if (registeredUser == null) {
            throw new BusinessException(PasswordCodes.EMAIL_IS_NOT_EXISTS.code(), PasswordCodes.EMAIL_IS_NOT_EXISTS.message());
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
