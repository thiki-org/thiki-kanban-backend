package org.thiki.kanban.login;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.FileUtil;
import org.thiki.kanban.foundation.security.md5.MD5Service;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.registration.Registration;
import org.thiki.kanban.registration.RegistrationPersistence;

import javax.annotation.Resource;
import java.security.KeyPairGenerator;

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

        Registration user = registrationPersistence.findByIdentity(identity, md5Password);

        Identification identification = new Identification();
        String token = tokenService.buildToken(user.getName());
        identification.setToken(token);

        return identification;
    }
}
