package org.thiki.kanban.login;

import org.hibernate.validator.constraints.NotEmpty;
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
    @Resource
    private UsersService usersService;
    @Resource
    private TokenService tokenService;
    @Resource
    private RSAService rsaService;

    @ValidateParams
    public Identification login(@NotNull(message = LoginCodes.IdentityIsRequired) String identity, @NotEmpty(message = LoginCodes.PasswordIsRequired) String password) throws Exception {
        User registeredUser = usersService.findByIdentity(identity);
        if (registeredUser == null) {
            throw new BusinessException(LoginCodes.USER_IS_NOT_EXISTS);
        }
        String rsaDecryptedPassword = rsaService.dencrypt(password);
        String md5Password = MD5Service.encrypt(rsaDecryptedPassword + registeredUser.getSalt());

        User matchedUser = usersService.findByCredential(identity, md5Password);
        if (matchedUser == null) {
            throw new InvalidParamsException(LoginCodes.USER_OR_PASSWORD_IS_INCORRECT.message());
        }

        Identification identification = new Identification();
        String token = tokenService.buildToken(matchedUser.getName());
        identification.setToken(token);
        identification.setUserName(matchedUser.getName());
        return identification;
    }
}
