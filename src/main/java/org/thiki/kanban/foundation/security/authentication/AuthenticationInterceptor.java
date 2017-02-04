package org.thiki.kanban.foundation.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thiki.kanban.foundation.configuration.ApplicationContextProvider;
import org.thiki.kanban.foundation.exception.UnauthorisedException;
import org.thiki.kanban.foundation.security.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by xubt on 03/02/2017.
 */

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest servletRequest, HttpServletResponse servletResponse, Object handler) throws Exception {
        String currentURL = servletRequest.getRequestURI();
        logger.info("authentication start.currentURL:{}", currentURL);
        String localAddress = servletRequest.getLocalAddr();
        String authentication = servletRequest.getHeader(Constants.HEADER_PARAMS_AUTHENTICATION);

        if (isLocalTestEnvironmentAndFreeAuthentication(localAddress, authentication)) {
            return true;
        }
        String userName = servletRequest.getHeader("userName");
        String method = servletRequest.getMethod();
        RolesResources rolesResources = ApplicationContextProvider.getApplicationContext().getBean(RolesResources.class);
        AuthenticationProviderFactory authenticationProviderFactory = ApplicationContextProvider.getApplicationContext().getBean(AuthenticationProviderFactory.class);

        MatchResult matchResult = rolesResources.match(currentURL, method);
        Authentication authenticationProvider = authenticationProviderFactory.loadProviderByRole(matchResult.getRoleName());
        if (authenticationProvider != null) {
            AuthenticationResult authenticateResult = authenticationProvider.authenticate(matchResult.getPathValues(), userName);
            if (authenticateResult.isFailed()) {
                throw new UnauthorisedException(authenticateResult);
            }
        }
        logger.info("authentication end.");
        return true;
    }

    private boolean isLocalTestEnvironmentAndFreeAuthentication(String localAddress, String authentication) {
        return Constants.LOCAL_ADDRESS.equals(localAddress) && Constants.FREE_IDENTIFICATION.equals(authentication);
    }
}
