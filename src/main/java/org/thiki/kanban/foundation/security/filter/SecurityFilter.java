package org.thiki.kanban.foundation.security.filter;

import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import org.thiki.kanban.foundation.security.Constants;
import org.thiki.kanban.foundation.security.token.IdentityResult;
import org.thiki.kanban.foundation.security.token.TokenService;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xubt on 7/10/16.
 */
@Service
@ConfigurationProperties(prefix = "security")
public class SecurityFilter implements Filter {
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
        String uri = ((RequestFacade) servletRequest).getRequestURI();
        String localAddress = servletRequest.getLocalAddr();
        String authentication = ((RequestFacade) servletRequest).getHeader(Constants.HEADER_PARAMS_AUTHENTICATION);

        if (isFreeSecurity(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (isLocalTestEnvironmentAndFreeAuthentication(localAddress, authentication)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (!isPassedSecurityVerify(servletRequest, servletResponse)) {
            return;
        }
        String token = ((RequestFacade) servletRequest).getHeader(Constants.HEADER_PARAMS_TOKEN);

        String updatedToken;
        try {
            updatedToken = tokenService.updateToken(token);
        } catch (Exception e) {
            ((ResponseFacade) servletResponse).sendRedirect(String.format("/unauthorised?code=%s&message=%s", Constants.SECURITY_IDENTIFY_UN_KNOW, "Update token failed:" + e.getMessage()));
            return;
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(Constants.HEADER_PARAMS_TOKEN, updatedToken);
        response.setHeader(Constants.ACCESS_CONTROL_EXPOSE_HEADERS, Constants.HEADER_PARAMS_TOKEN);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isLocalTestEnvironmentAndFreeAuthentication(String localAddress, String authentication) {
        return Constants.LOCAL_ADDRESS.equals(localAddress) && Constants.FREE_AUTHENTICATION.equals(authentication);
    }

    @Override
    public void destroy() {

    }

    private boolean isPassedSecurityVerify(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        String token = ((RequestFacade) servletRequest).getHeader(Constants.HEADER_PARAMS_TOKEN);
        String userName = ((RequestFacade) servletRequest).getHeader(Constants.HEADER_PARAMS_USER_NAME);

        try {
            IdentityResult identityResult = tokenService.identify(token, userName);
            if (identityResult.getErrorCode().equals(Constants.SECURITY_IDENTIFY_PASSED_CODE)) {
                return true;
            }
            servletResponse.setCharacterEncoding("UTF-8");
            ((ResponseFacade) servletResponse).sendRedirect(String.format("/unauthorised?code=%s&message=%s", identityResult.getErrorCode(), URLEncoder.encode(identityResult.getErrorMessage(), "UTF-8")));
            return false;
        } catch (Exception e) {
            ((ResponseFacade) servletResponse).sendRedirect(String.format("/error?code=%s&message=%s", Constants.SECURITY_IDENTIFY_UN_KNOW, URLEncoder.encode(e.getMessage(), "UTF-8")));
            return false;
        }
    }
}
