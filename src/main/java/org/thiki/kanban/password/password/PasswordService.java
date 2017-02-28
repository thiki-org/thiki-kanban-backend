package org.thiki.kanban.password.password;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.VerificationCodeService;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.mail.MailService;
import org.thiki.kanban.foundation.security.identification.rsa.RSAService;
import org.thiki.kanban.password.passwordReset.PasswordReset;
import org.thiki.kanban.password.passwordReset.PasswordResetApplication;
import org.thiki.kanban.password.passwordRetrieval.PasswordRetrievalApplication;
import org.thiki.kanban.password.passwordRetrieval.VerificationCodeEmailData;
import org.thiki.kanban.user.User;
import org.thiki.kanban.user.UsersService;

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
    private UsersService usersService;
    @Resource
    private RSAService rsaService;

    @Resource
    private MailService mailService;

    public String applyRetrieval(PasswordRetrievalApplication passwordRetrievalApplication) throws TemplateException, IOException, MessagingException {
        User registeredUser = usersService.findByEmail(passwordRetrievalApplication.getEmail());
        verifyWhetherUserIsExists(registeredUser);

        String verificationCode = verificationCodeService.generate();

        passwordRetrievalApplication.setVerificationCode(verificationCode);

        passwordPersistence.clearUnfinishedApplication(registeredUser.getUserName());

        passwordRetrievalApplication.setUserName(registeredUser.getUserName());
        passwordPersistence.createPasswordRetrievalApplication(passwordRetrievalApplication);

        sendVerificationCodeEmail(registeredUser, verificationCode);
        return registeredUser.getUserName();
    }

    public void applyReset(String userName, PasswordResetApplication passwordResetApplication) {
        PasswordRetrievalApplication passwordRetrievalApplication = passwordPersistence.loadRetrievalApplication(userName);

        verifyRetrievalApplicationIsNotNull(passwordRetrievalApplication);
        passwordRetrievalApplication.verifyVerificationCodeIsCorrect(passwordResetApplication.getVerificationCode());
        passwordRetrievalApplication.verifyVerificationCodeIsNotExpired(passwordRetrievalApplication.getCreationTime());

        passwordPersistence.makeRetrievalApplicationPassed(userName);
        passwordResetApplication.setUserName(userName);
        passwordPersistence.createPasswordResetApplication(passwordResetApplication);
    }

    public void resetPassword(String userName, PasswordReset passwordReset) throws Exception {
        boolean isPasswordResetApplicationExists = passwordPersistence.isPasswordResetApplicationExists(userName);
        if (!isPasswordResetApplicationExists) {
            throw new BusinessException(PasswordCodes.NO_PASSWORD_RESET_RECORD.code(), PasswordCodes.NO_PASSWORD_RESET_RECORD.message());
        }
        User registeredUser = usersService.findByIdentity(userName);
        String dencryptPassword = rsaService.dencrypt(passwordReset.getPassword());


        String encryptedPassword = passwordReset.encryptPassword(registeredUser.getSalt(), dencryptPassword);

        passwordPersistence.resetPassword(userName, encryptedPassword);
        passwordPersistence.cleanResetApplication(userName);
    }

    private void verifyWhetherUserIsExists(User registeredUser) {
        if (registeredUser == null) {
            throw new BusinessException(PasswordCodes.EMAIL_IS_NOT_EXISTS.code(), PasswordCodes.EMAIL_IS_NOT_EXISTS.message());
        }
    }

    private void verifyRetrievalApplicationIsNotNull(PasswordRetrievalApplication passwordRetrievalApplication) {
        if (passwordRetrievalApplication == null) {
            throw new BusinessException(PasswordCodes.NO_PASSWORD_RETRIEVAL_RECORD.code(), PasswordCodes.NO_PASSWORD_RETRIEVAL_RECORD.message());
        }
    }

    private void sendVerificationCodeEmail(User registeredUser, String verificationCode) throws TemplateException, IOException, MessagingException {
        VerificationCodeEmailData verificationCodeEmailData = new VerificationCodeEmailData();
        verificationCodeEmailData.setReceiver(registeredUser.getEmail());
        verificationCodeEmailData.setUserName(registeredUser.getUserName());
        verificationCodeEmailData.setVerificationCode(verificationCode);
        mailService.sendMailByTemplate(verificationCodeEmailData, passwordRetrievalEmailTemplate);
    }
}
