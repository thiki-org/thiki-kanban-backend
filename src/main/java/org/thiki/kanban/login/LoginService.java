package org.thiki.kanban.login;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.FileUtil;
import org.thiki.kanban.foundation.security.md5.MD5Service;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.foundation.security.token.TokenService;
import org.thiki.kanban.registration.Registration;
import org.thiki.kanban.registration.RegistrationPersistence;

import javax.annotation.Resource;
import java.security.InvalidParameterException;
import java.security.KeyPairGenerator;
import java.text.MessageFormat;

/**
 * Created by xubt on 7/5/16.
 */
@Service
public class LoginService {
    @Resource
    RegistrationPersistence registrationPersistence;
    @Resource
    TokenService tokenService;

    public PublicKey generatePubicKey(String userName) throws Exception {
        Registration registeredUser = registrationPersistence.findByName(userName);
        if (registeredUser == null) {
            throw new InvalidParameterException(MessageFormat.format("user[{0}] is not found.", userName));
        }
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        PublicKey publicPublicKey = new PublicKey();
        String publicKeyContent = FileUtil.readFile("src/main/resources/rsakey.pub");
        publicPublicKey.setPublicKey(publicKeyContent);
        return publicPublicKey;
    }

    public Identification login(String identity, String password) throws Exception {
        Registration registeredUser = registrationPersistence.findByName(identity);
        String rsaDecryptedPassword = RSAService.decrypt(password);
        String md5Password = MD5Service.encrypt(rsaDecryptedPassword + registeredUser.getSalt());

        Registration matchedUser = registrationPersistence.findByIdentity(identity, md5Password);

        if (matchedUser == null) {
            throw new InvalidParameterException("Your username or password is incorrect.");
        }

        Identification identification = new Identification();
        String token = tokenService.buildToken(matchedUser.getName());
        identification.setToken(token);

        return identification;
    }
}
