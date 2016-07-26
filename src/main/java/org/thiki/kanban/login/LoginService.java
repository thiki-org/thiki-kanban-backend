package org.thiki.kanban.login;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.config.ValidateParams;
import org.thiki.kanban.foundation.security.md5.MD5Service;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.foundation.security.token.TokenService;
import org.thiki.kanban.registration.Registration;
import org.thiki.kanban.registration.RegistrationPersistence;

import javax.annotation.Resource;
import java.security.InvalidParameterException;

/**
 * Created by xubt on 7/5/16.
 */
@Service
public class LoginService {
    @Resource
    private RegistrationPersistence registrationPersistence;
    @Resource
    private TokenService tokenService;
    @Resource
    private RSAService rsaService;

    @ValidateParams
    public Identification login(@NotEmpty(message = "Identity is required.") String identity, @NotEmpty(message = "Password is required.") String password) throws Exception {
        Registration registeredUser = registrationPersistence.findByName(identity);
        String rsaDecryptedPassword = rsaService.dencrypt(password);
        String md5Password = MD5Service.encrypt(rsaDecryptedPassword + registeredUser.getSalt());

        Registration matchedUser = registrationPersistence.findByIdentity(identity, md5Password);
        if (matchedUser == null) {
            throw new InvalidParameterException("Your username or password is incorrect.");
        }

        Identification identification = new Identification();
        String token = tokenService.buildToken(matchedUser.getName());
        identification.setToken(token);
        identification.setUserName(matchedUser.getName());
        return identification;
    }
}
