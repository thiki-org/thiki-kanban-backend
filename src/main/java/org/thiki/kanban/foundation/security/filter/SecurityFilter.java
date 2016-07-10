package org.thiki.kanban.foundation.security.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.http.HttpStatus;
import org.thiki.kanban.foundation.exception.ExceptionCode;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by xubt on 7/10/16.
 */
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = ((RequestFacade) servletRequest).getHeader("token");

        String localAddress = servletRequest.getLocalAddr();
        String authentication = ((RequestFacade) servletRequest).getHeader("authentication");
        if (!isLocalTestEnvironment(localAddress, authentication) && isTokenInvalid(token)) {
            JSONObject responseBody = new JSONObject();
            responseBody.put("message", "Token is required,please authenticate first.");
            responseBody.put("code", ExceptionCode.UNAUTHORIZED.code());
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
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            PrintWriter out = response.getWriter();
            out.print(responseBody.toJSONString());
            out.flush();
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isLocalTestEnvironment(String localAddress, String authentication) {
        return "127.0.0.1".equals(localAddress) && "no".equals(authentication);
    }

    private boolean isTokenInvalid(String token) {
        return token == null || token.equals("");
    }

    @Override
    public void destroy() {

    }
}
