package org.thiki.kanban.foundation.security.authentication;

import org.apache.catalina.connector.RequestFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.UnauthorisedException;
import org.thiki.kanban.foundation.security.Constants;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * Created by xubt on 10/24/16.
 */
@Service
@ConfigurationProperties(prefix = "security")
public class AuthenticationFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    @Resource
    private RolesResources rolesResources;
    @Resource
    private AuthenticationProviderFactory authenticationProviderFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String currentURL = ((RequestFacade) servletRequest).getRequestURI();
        logger.info("authentication start.currentURL:{}", currentURL);
        String localAddress = servletRequest.getLocalAddr();
        String authentication = ((RequestFacade) servletRequest).getHeader(Constants.HEADER_PARAMS_AUTHENTICATION);

        if (isLocalTestEnvironmentAndFreeAuthentication(localAddress, authentication)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String userName = ((RequestFacade) servletRequest).getHeader("userName");
        String method = ((RequestFacade) servletRequest).getMethod();

        MatchResult matchResult = rolesResources.match(currentURL, method);
        Authentication authenticationProvider = authenticationProviderFactory.loadProviderByRole(matchResult.getRoleName());
        if (authenticationProvider != null) {
            AuthenticationResult authenticateResult = authenticationProvider.authenticate(matchResult.getPathValues(), userName);
            if (authenticateResult.isFailed()) {
                throw new UnauthorisedException(authenticateResult);
            }
        }
        logger.info("authentication end.");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private boolean isLocalTestEnvironmentAndFreeAuthentication(String localAddress, String authentication) {
        return Constants.LOCAL_ADDRESS.equals(localAddress) && Constants.FREE_IDENTIFICATION.equals(authentication);
    }
}
