package org.thiki.kanban.foundation.security.identification.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UriTemplate;
import org.thiki.kanban.foundation.configuration.ApplicationContextProvider;
import org.thiki.kanban.foundation.configuration.ApplicationProperties;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.UnauthorisedException;
import org.thiki.kanban.foundation.security.Constants;
import org.thiki.kanban.foundation.security.identification.token.IdentityResult;
import org.thiki.kanban.foundation.security.identification.token.TokenService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xubt on 03/02/2017.
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    protected String contextPath;

    public SecurityInterceptor(String contextPath) {
        this.contextPath = contextPath;
    }

    private boolean isFreeSecurity(String uri) {
        ApplicationProperties applicationProperties = ApplicationContextProvider.getApplicationContext().getBean(ApplicationProperties.class);
        for (String freeSecurityUrl : applicationProperties.getWhiteList()) {
            UriTemplate uriTemplate = new UriTemplate(contextPath + freeSecurityUrl);
            if (uriTemplate.matches(uri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Object handler) throws Exception {
        String userName = servletRequest.getHeader(Constants.HEADER_PARAMS_USER_NAME);
        String uri = servletRequest.getRequestURI();
        logger.info("identification start.userName:{},uri:{}", userName, uri);
        String localAddress = servletRequest.getLocalAddr();
        String authentication = servletRequest.getHeader(Constants.HEADER_PARAMS_IDENTIFICATION);

        if (isFreeSecurity(uri)) {
            return true;
        }
        if (isLocalTestEnvironmentAndFreeAuthentication(localAddress, authentication)) {
            return true;
        }
        String updatedToken;

        TokenService tokenService = ApplicationContextProvider.getApplicationContext().getBean(TokenService.class);
        try {
            String token = servletRequest.getHeader(Constants.HEADER_PARAMS_TOKEN);
            if (!isPassedSecurityVerify(token, userName, tokenService)) {
                return true;
            }
            updatedToken = tokenService.updateToken(token);
        } catch (BusinessException businessException) {
            throw new UnauthorisedException(businessException.getCode(), businessException.getMessage());
        } catch (Exception businessException) {
            throw new UnauthorisedException(Constants.SECURITY_IDENTIFY_UN_KNOW, businessException.getMessage());
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(Constants.HEADER_PARAMS_TOKEN, updatedToken);
        response.setHeader(Constants.ACCESS_CONTROL_EXPOSE_HEADERS, Constants.HEADER_PARAMS_TOKEN);
        logger.info("identification end.");

        return true;
    }

    private boolean isLocalTestEnvironmentAndFreeAuthentication(String localAddress, String authentication) {
        return Constants.LOCAL_ADDRESS.equals(localAddress) && Constants.FREE_IDENTIFICATION.equals(authentication);
    }

    private boolean isPassedSecurityVerify(String token, String userName, TokenService tokenService) throws Exception {
        IdentityResult identityResult = tokenService.identify(token, userName);
        if (identityResult.getErrorCode() == (Constants.SECURITY_IDENTIFY_PASSED_CODE)) {
            return true;
        }
        throw new UnauthorisedException(identityResult.getErrorCode(), identityResult.getErrorMessage());
    }
}
