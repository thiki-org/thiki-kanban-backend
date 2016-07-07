package org.thiki.kanban.login;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.FileUtil;

import java.security.KeyPairGenerator;

/**
 * Created by xubt on 7/5/16.
 */
@Service
public class IdentificationService {

    public PublicKey generatePubicKey(String userName) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        PublicKey publicPublicKey = new PublicKey();
        String publicKeyContent = FileUtil.readFile("src/main/resources/rsakey.pub");
        publicPublicKey.setPublicKey(publicKeyContent);
        return publicPublicKey;
    }
}
