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
        if (token == null || token.equals("")) {
            JSONObject responseBody = new JSONObject();
            responseBody.put("message", "Authentication is required.");
            responseBody.put("code", ExceptionCode.UNAUTHORIZED.code());
            responseBody.put("_links", new HashMap<String, Object>() {
                {
                    put("registration", new HashMap<String, String>() {
                        {
                            {
                                put("href", "/registration");
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

    @Override
    public void destroy() {

    }
}
