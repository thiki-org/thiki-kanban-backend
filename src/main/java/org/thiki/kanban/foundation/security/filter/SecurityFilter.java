package org.thiki.kanban.foundation.security.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;
import org.thiki.kanban.foundation.security.Constants;
import org.thiki.kanban.foundation.security.token.IdentityResult;
import org.thiki.kanban.foundation.security.token.TokenService;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xubt on 7/10/16.
 */
@Service
public class SecurityFilter implements Filter {
    @Resource
    private TokenService tokenService;

    private List<String> whiteList = new ArrayList<String>() {
        {
            add("/entrance");
            add("/publicKey");
            add("/registration");
            add("/login");
            add("/passwordRetrievalApplication");
            add("/{userName}/passwordResetApplication");
            add("/{userName}/password");
        }
    };

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
            writeResponse(servletResponse, "Update token failed:" + e.getMessage(), Constants.SECURITY_IDENTIFY_UN_KNOW, HttpStatus.UNAUTHORIZED.value());
            return;
        }
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(Constants.HEADER_PARAMS_TOKEN, updatedToken);
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
            writeResponse(servletResponse, identityResult.getErrorMessage(), identityResult.getErrorCode(), HttpStatus.UNAUTHORIZED.value());
            return false;
        } catch (Exception e) {
            writeResponse(servletResponse, "Error occurred when parsing the token:" + e.getMessage(), Constants.SECURITY_IDENTIFY_UN_KNOW, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return false;
        }
    }

    private void writeResponse(ServletResponse servletResponse, String message, String code, int httpCode) throws IOException {
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", message);
        responseBody.put("code", code);
        responseBody.put("_links", new HashMap<String, Object>() {
            {
                put("identification", new HashMap<String, String>() {
                    {
                        {
                            put("href", "/identification");
                        }
                    }
                });
            }
        });
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json");
        response.setStatus(httpCode);
        PrintWriter out = response.getWriter();
        out.print(responseBody.toJSONString());
        out.flush();
    }
}
