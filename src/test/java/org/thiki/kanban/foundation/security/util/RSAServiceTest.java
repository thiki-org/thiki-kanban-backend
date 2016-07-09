package org.thiki.kanban.foundation.security.util;

import org.junit.Test;
import org.thiki.kanban.foundation.security.rsa.RSAService;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 7/7/16.
 */
public class RSAServiceTest {
    @Test
    public void RSA_Encrypt_Dencrypt() throws Exception {
        String contentToEncrypt = "hello-思奇";

        PublicKey publicKey = RSAService.getPemPublicKey("src/main/resources/rsakey.pub");
        String encryptString = RSAService.encryptString(publicKey, contentToEncrypt);

        PrivateKey privateKey = RSAService.getPemPrivateKey("src/main/resources/rsakey.pem");
        String decryptString = RSAService.decryptString(privateKey, encryptString);

        assertEquals(contentToEncrypt, decryptString);
    }
}
