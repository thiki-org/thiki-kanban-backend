package org.thiki.kanban.foundation.security.token;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.date.DateUtil;
import org.thiki.kanban.foundation.security.Constants;
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

    public String buildToken(String userName) throws Exception {
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setUserName(userName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date now = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(now);
        rightNow.add(Calendar.MINUTE, 5);

        Date expirationTime = rightNow.getTime();
        String expirationTimeStr = sdf.format(expirationTime);
        authenticationToken.setExpirationTime(expirationTimeStr);
        String encryptedToken = rsaService.encryptWithDefaultKey(authenticationToken.toString());

        return encryptedToken;
    }

    public boolean isExpired(String token) throws Exception {
        String decryptedToken = rsaService.dencryptWithDefaultKey(token);
        AuthenticationToken authenticationToken = JSON.parseObject(decryptedToken, AuthenticationToken.class);

        Date expiredTime = DateUtil.StringToDate(authenticationToken.getExpirationTime(), "yyyyMMddHHmmss");
        return expiredTime.before(new Date());
    }

    public boolean isTampered(String token, String userName) throws Exception {
        String decryptedToken = rsaService.dencryptWithDefaultKey(token);
        AuthenticationToken authenticationToken = JSON.parseObject(decryptedToken, AuthenticationToken.class);

        return !authenticationToken.getUserName().equals(userName);
    }

    public String identify(String token, String userName, String authentication, String localAddress) throws Exception {
        if (isLocalTestEnvironmentAndFreeAuthentication(localAddress, authentication)) {
            return Constants.SECURITY_IDENTIFY_PASSED;
        }

        if (isTokenEmpty(token)) {
            return Constants.SECURITY_IDENTITY_NO_AUTHENTICATION_TOKEN;
        }
        if (isExpired(token)) {
            return Constants.SECURITY_IDENTITY_AUTHENTICATION_TOKEN_HAS_EXPIRE;
        }
        if (isTampered(token, userName)) {
            return Constants.SECURITY_IDENTITY_USER_NAME_IS_NOT_CONSISTENT;
        }
        return Constants.SECURITY_IDENTIFY_PASSED;
    }

    private boolean isLocalTestEnvironmentAndFreeAuthentication(String localAddress, String authentication) {
        return Constants.LOCAL_ADDRESS.equals(localAddress) && Constants.FREE_AUTHENTICATION.equals(authentication);
    }

    private boolean isTokenEmpty(String token) {
        return token == null || token.equals("");
    }
}
