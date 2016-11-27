package org.thiki.kanban.foundation.security.identification.filter;

import org.apache.catalina.connector.RequestFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.UnauthorisedException;
import org.thiki.kanban.foundation.security.Constants;
import org.thiki.kanban.foundation.security.identification.token.IdentityResult;
import org.thiki.kanban.foundation.security.identification.token.TokenService;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubt on 7/10/16.
 */
@Service
@ConfigurationProperties(prefix = "security")
public class SecurityFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
    @Resource
    private TokenService tokenService;

    private List<String> whiteList = new ArrayList<>();

    public List<String> getWhiteList() {
        return whiteList;
    }

    private boolean isFreeSecurity(String uri) {
        for (String freeSecurityUrl : whiteList) {
            UriTemplate uriTemplate = new UriTemplate(freeSecurityUrl);
            if (uriTemplate.matches(uri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("identification start.");
        String uri = ((RequestFacade) servletRequest).getRequestURI();
        String localAddress = servletRequest.getLocalAddr();
        String authentication = ((RequestFacade) servletRequest).getHeader(Constants.HEADER_PARAMS_IDENTIFICATION);

        if (isFreeSecurity(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (isLocalTestEnvironmentAndFreeAuthentication(localAddress, authentication)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String updatedToken;
        try {
            if (!isPassedSecurityVerify(servletRequest, servletResponse)) {
                return;
            }
            String token = ((RequestFacade) servletRequest).getHeader(Constants.HEADER_PARAMS_TOKEN);
            updatedToken = tokenService.updateToken(token);
        } catch (BusinessException businessException) {
            throw new UnauthorisedException(businessException.getCode(), businessException.getMessage());
        } catch (Exception businessException) {
            throw new UnauthorisedException(Constants.SECURITY_IDENTIFY_UN_KNOW, businessException.getMessage());
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(Constants.HEADER_PARAMS_TOKEN, updatedToken);
        response.setHeader(Constants.ACCESS_CONTROL_EXPOSE_HEADERS, Constants.HEADER_PARAMS_TOKEN);
        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("identification end.");
    }

    private boolean isLocalTestEnvironmentAndFreeAuthentication(String localAddress, String authentication) {
        return Constants.LOCAL_ADDRESS.equals(localAddress) && Constants.FREE_IDENTIFICATION.equals(authentication);
    }

    @Override
    public void destroy() {

    }

    private boolean isPassedSecurityVerify(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        String token = ((RequestFacade) servletRequest).getHeader(Constants.HEADER_PARAMS_TOKEN);
        String userName = ((RequestFacade) servletRequest).getHeader(Constants.HEADER_PARAMS_USER_NAME);

        IdentityResult identityResult = tokenService.identify(token, userName);
        if (identityResult.getErrorCode() == (Constants.SECURITY_IDENTIFY_PASSED_CODE)) {
            return true;
        }
        throw new UnauthorisedException(identityResult.getErrorCode(), identityResult.getErrorMessage());
    }
}
