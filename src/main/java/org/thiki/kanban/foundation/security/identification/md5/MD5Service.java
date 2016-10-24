package org.thiki.kanban.foundation.security.identification.md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by xubt on 7/7/16.
 */
public class MD5Service {
    public static String encrypt(String string) {
        MessageDigest m = null;
        String result = "";
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(string.getBytes("UTF8"));
            byte s[] = m.digest();
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
