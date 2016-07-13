package org.thiki.kanban.publickey;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.registration.Registration;
import org.thiki.kanban.registration.RegistrationPersistence;

import javax.annotation.Resource;
import java.security.InvalidParameterException;
import java.text.MessageFormat;

/**
 * Created by xubt on 7/5/16.
 */
@Service
public class PublicKeyService {
    @Resource
    private RegistrationPersistence registrationPersistence;
    @Resource
    private RSAService rsaService;

    public PublicKey authenticate(String userName) throws Exception {
        Registration registeredUser = registrationPersistence.findByName(userName);
        if (registeredUser == null) {
            throw new InvalidParameterException(MessageFormat.format("No user named {0} is found.", userName));
        }
        PublicKey publicPublicKey = new PublicKey();
        String publicKeyContent = rsaService.loadDefaultPublicKey();
        publicPublicKey.setPublicKey(publicKeyContent);
        return publicPublicKey;
    }
}
