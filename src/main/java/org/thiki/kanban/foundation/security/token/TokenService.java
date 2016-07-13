package org.thiki.kanban.foundation.security.token;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.common.date.DateService;
import org.thiki.kanban.foundation.common.date.DateStyle;
import org.thiki.kanban.foundation.security.Constants;
import org.thiki.kanban.foundation.security.rsa.RSAService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by xubt on 7/8/16.
 */
@Service
public class TokenService {
    @Resource
    public RSAService rsaService;

    @Resource
    private DateService dateService;

    public String buildToken(String userName) throws Exception {
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setUserName(userName);
        Date expirationTime = dateService.addMinute(new Date(), Constants.TOKEN_EXPIRED_TIME);
        String expirationTimeStr = dateService.DateToString(expirationTime, DateStyle.YYYY_MM_DD_HH_MM_SS);

        authenticationToken.setExpirationTime(expirationTimeStr);
        return rsaService.encryptWithDefaultKey(authenticationToken.toString());
    }

    public boolean isExpired(String token) throws Exception {
        String decryptedToken = rsaService.dencryptWithDefaultKey(token);
        AuthenticationToken authenticationToken = JSON.parseObject(decryptedToken, AuthenticationToken.class);

        Date expiredTime = dateService.StringToDate(authenticationToken.getExpirationTime(), DateStyle.YYYY_MM_DD_HH_MM_SS);
        return expiredTime.before(new Date());
    }

    public boolean isTampered(String token, String userName) throws Exception {
        String decryptedToken = rsaService.dencryptWithDefaultKey(token);
        AuthenticationToken authenticationToken = JSON.parseObject(decryptedToken, AuthenticationToken.class);

        return !authenticationToken.getUserName().equals(userName);
    }

    public String identify(String token, String userName, String authentication, String localAddress) throws Exception {
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


    private boolean isTokenEmpty(String token) {
        return token == null || token.equals("");
    }

    public String updateToken(String token) throws Exception {
        String decryptedToken = rsaService.dencryptWithDefaultKey(token);
        AuthenticationToken authenticationToken = JSON.parseObject(decryptedToken, AuthenticationToken.class);

        return buildToken(authenticationToken.getUserName());
    }
}
