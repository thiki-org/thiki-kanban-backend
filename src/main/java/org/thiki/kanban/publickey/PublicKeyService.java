package org.thiki.kanban.publickey;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.security.rsa.RSAService;

import javax.annotation.Resource;

/**
 * Created by xubt on 7/5/16.
 */
@Service
public class PublicKeyService {
    @Resource
    private RSAService rsaService;

    public PublicKey authenticate() throws Exception {
        PublicKey publicPublicKey = new PublicKey();
        String publicKeyContent = rsaService.loadDefaultPublicKey();
        publicPublicKey.setPublicKey(publicKeyContent);
        return publicPublicKey;
    }
}
