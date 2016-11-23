package org.thiki.kanban.login;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.aspect.ValidateParams;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.InvalidParamsException;
import org.thiki.kanban.foundation.security.identification.md5.MD5Service;
import org.thiki.kanban.foundation.security.identification.rsa.RSAService;
import org.thiki.kanban.foundation.security.identification.token.TokenService;
import org.thiki.kanban.user.User;
import org.thiki.kanban.user.UsersService;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * Created by xubt on 7/5/16.
 */
@Service
public class LoginService {
    public static Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Resource
    private UsersService usersService;
    @Resource
    private TokenService tokenService;
    @Resource
    private RSAService rsaService;

    @ValidateParams
    public Identification login(@NotNull(message = LoginCodes.IdentityIsRequired) String identity, @NotEmpty(message = LoginCodes.PasswordIsRequired) String password) throws Exception {
        logger.info("Login request arrived service,identity:" + identity);
        User registeredUser = usersService.findByIdentity(identity);
        logger.info("Load user:" + registeredUser);

        if (registeredUser == null) {
            throw new BusinessException(LoginCodes.USER_IS_NOT_EXISTS);
        }
        logger.info("Dencrypt password.");
        String rsaDecryptedPassword = rsaService.dencrypt(password);
        logger.info("Encrypt password with md5.");
        String md5Password = MD5Service.encrypt(rsaDecryptedPassword + registeredUser.getSalt());

        User matchedUser = usersService.findByCredential(identity, md5Password);
        logger.info("Matched result:" + matchedUser);
        if (matchedUser == null) {
            throw new InvalidParamsException(LoginCodes.USER_OR_PASSWORD_IS_INCORRECT.message());
        }

        Identification identification = new Identification();
        String token = tokenService.buildToken(matchedUser.getName());
        identification.setToken(token);
        identification.setUserName(matchedUser.getName());
        logger.info("Login successfully:" + identification);
        return identification;
    }
}
