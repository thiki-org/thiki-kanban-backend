package org.thiki.kanban.foundation.security.rsa;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.FileUtil;
import org.thiki.kanban.foundation.exception.InvalidParamsException;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by xubt on 7/6/16.
 */
@Service
public class RSAService {
    public static final String RSA_ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";

    private static final String ALGORITHM = "RSA";

    private static String privateKeyPath = "src/main/resources/rsakey.pem";
    private static String publicKeyPath = "src/main/resources/rsakey.pub";

    public String encrypt(String publicKey, String plaintext) throws Exception {
        return Base64.encodeBase64String(encryptAsByteArray(plaintext, getPublicKey(publicKey)));
    }

    public String loadKey(String keyPath) {
        return FileUtil.readFile(keyPath);
    }

    public String encrypt(String plaintext) throws Exception {
        if (plaintext == null) {
            return "";
        }
        String publicKeyContent = FileUtil.readFile(publicKeyPath);
        PublicKey publicKey = getPublicKey(publicKeyContent);
        return Base64.encodeBase64String(encryptAsByteArray(plaintext, publicKey));
    }

    public byte[] encryptAsByteArray(String data, PublicKey publicKey) {
        try {
            if (data == null) {
                return null;
            }
            Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            throw new IllegalArgumentException("Encrypt failed!", e);
        }
    }

    public PublicKey getPublicKey(String base64PublicKey) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(base64PublicKey));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to get public key!", e);
        }
    }

    public PrivateKey getPrivateKey(String base64PrivateKey) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(base64PrivateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            throw new InvalidParamsException("Failed to get private key!");
        }
    }

    public String loadDefaultPublicKey() throws Exception {
        return loadKey(publicKeyPath);
    }

    public String dencrypt(String plaintext) throws Exception {
        String privateKeyContent = FileUtil.readFile(privateKeyPath);
        return decrypt(Base64.decodeBase64(plaintext), getPrivateKey(privateKeyContent));
    }

    public String decrypt(byte[] data, PrivateKey privateKey) {
        try {
            if (data == null) {
                return "";
            }
            Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(data));
        } catch (Exception e) {
            throw new InvalidParamsException("通过私钥解密失败,请确保数据已经通过公钥加密。");
        }
    }
}
