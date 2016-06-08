package org.thiki.kanban.foundation.common.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <ul>
 * <li>BASE64的加密解密是双向的，可以求反解。</li>
 * <li>MD5、SHA以及HMAC是单向加密，任何数据加密后只会产生唯一的一个加密串，通常用来校验数据在传输过程中是否被修改。</li>
 * <li>HMAC算法有一个密钥，增强了数据传输过程中的安全性，强化了算法外的不可控因素。</li>
 * <li>DES DES-Data Encryption Standard,即数据加密算法。
 * DES算法的入口参数有三个:Key、Data、Mode。
 * <ul>
 * <li>Key:8个字节共64位,是DES算法的工作密钥;</li>
 * <li>Data:8个字节64位,是要被加密或被解密的数据;</li>
 * <li>Mode:DES的工作方式,有两种:加密或解密。</li>
 * </ul>
 * </li>
 * <ul>
 *
 * @author Ice_Liu
 *
 */
public class CryptUtil {
    private static final String KEY_MD5 = "MD5";
    private static final String KEY_SHA = "SHA";
    /**
     * MAC算法可选以下多种算法
     *
     * <pre>
     *
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacSHA256";

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64 加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptMD5(byte[] data) throws Exception {

        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);

        return md5.digest();

    }

    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }

    /**
     * 初始化HMAC密钥
     *
     * @return
     * @throws Exception
     */
    public static String initMacKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }

    /**
     * HMAC 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }

    /**
     * DES 算法 <br>
     * 可替换为以下任意一种算法，同时key值的size相应改变。
     *
     * <pre>
     * DES                  key size must be equal to 56
     * DESede(TripleDES)    key size must be equal to 112 or 168
     * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available

     * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive)

     * RC2                  key size must be between 40 and 1024 bits
     * RC4(ARCFOUR)         key size must be between 40 and 1024 bits
     * </pre>
     */
    public static final String ALGORITHM = "DES";

    /**
     * DES 算法转换密钥<br>
     *
     * @param key
     * @return
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        SecretKey secretKey = null;
        if (ALGORITHM.equals("DES") || ALGORITHM.equals("DESede")) {
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            secretKey = keyFactory.generateSecret(dks);
        } else {
            // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
            secretKey = new SecretKeySpec(key, ALGORITHM);
        }
        return secretKey;
    }

    /**
     * DES 算法解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * DES 算法加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, String key) throws Exception {
        Key k = toKey(decryptBASE64(key));
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    /**
     * DES 算法生成密钥
     *
     * @return
     * @throws Exception
     */
    public static String initKey() throws Exception {
        return initKey(null);
    }

    /**
     * DES 算法生成密钥
     *
     * @param seed
     * @return
     * @throws Exception
     */
    public static String initKey(String seed) throws Exception {
        SecureRandom secureRandom = null;
        if (seed != null) {
            secureRandom = new SecureRandom(decryptBASE64(seed));
        } else {
            secureRandom = new SecureRandom();
        }
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
        kg.init(secureRandom);
        SecretKey secretKey = kg.generateKey();
        return encryptBASE64(secretKey.getEncoded());
    }


    public static void main(String[] args) {
        try {
            String s = "1|1|1597393163|data|2468";
            String b = CryptUtil.encryptBASE64(s.getBytes("UTF-8"));
            System.out.println("BASE64加密后:" + b);
            byte[] c = CryptUtil.decryptBASE64(b);
            System.out.println("BASE64解密后:" + new String(c, "UTF-8"));

            c = encryptMD5(s.getBytes());
            System.out.println("MD5   加密后:" + new BigInteger(c).toString(16));

            c = encryptSHA(s.getBytes());
            System.out.println("SHA   加密后:" + new BigInteger(c).toString(16));

            String key = initMacKey();
            System.out.println("HMAC密匙:" + key);
            c = encryptHMAC(s.getBytes(), key);
            System.out.println("HMAC  加密后:" + new BigInteger(c).toString(16));

            key = initKey();
            System.out.println(ALGORITHM + "密钥:t" + key);
            c = encrypt(s.getBytes("UTF-8"), "thoqdJpHBlzQ=");
            System.out.println(ALGORITHM + "   加密后:" + new BigInteger(c).toString(16));
            c = decrypt(c,  "thoqdJpHBlzQ=");
            System.out.println(ALGORITHM + "   解密后:" + new String(c, "UTF-8"));
            //userid|is_login|token expires|data（相关信息）|4位随机值
//            String s = "1|1|1597393163|data|2468";
//            byte[]  c=encrypt(s.getBytes("UTF-8"), "thoqdJpHBlzQ=");
//            System.out.println(ALGORITHM + "   解密后:" + new String(c, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

