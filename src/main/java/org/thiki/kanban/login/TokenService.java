package org.thiki.kanban.login;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.security.rsa.RSAService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xubt on 7/8/16.
 */
@Service
public class TokenService {
    @Resource
    public RSAService rsaService;

    public String buildToken(String name) throws Exception {
        Token token = new Token();
        token.setUserName(name);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(now);
        rightNow.add(Calendar.MINUTE, 5);

        Date expirationTime = rightNow.getTime();
        String expirationTimeStr = sdf.format(expirationTime);
        token.setExpirationTime(expirationTimeStr);
        String encryptedToken = rsaService.encryptWithDefaultKey(token.toString());

        return encryptedToken;
    }
}
