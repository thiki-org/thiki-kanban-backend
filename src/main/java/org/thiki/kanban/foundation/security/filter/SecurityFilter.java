package org.thiki.kanban.foundation.security.filter;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.ExceptionCode;
import org.thiki.kanban.foundation.security.token.TokenService;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * Created by xubt on 7/10/16.
 */
@Service
public class SecurityFilter implements Filter {
    @Resource
    private TokenService tokenService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isPassSecurityVerify(servletRequest, servletResponse)) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean isLocalTestEnvironment(String localAddress, String authentication) {
        return "127.0.0.1".equals(localAddress) && "no".equals(authentication);
    }

    private boolean isTokenEmpty(String token) {
        return token == null || token.equals("");
    }

    @Override
    public void destroy() {

    }

    private boolean isPassSecurityVerify(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        String token = ((RequestFacade) servletRequest).getHeader("token");
        String userName = ((RequestFacade) servletRequest).getHeader("userName");

        String localAddress = servletRequest.getLocalAddr();
        String authentication = ((RequestFacade) servletRequest).getHeader("authentication");
        if (isLocalTestEnvironment(localAddress, authentication)) return true;

        if (isTokenEmpty(token)) {
            JSONObject responseBody = new JSONObject();
            responseBody.put("message", "AuthenticationToken is required,please authenticate first.");
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
            return false;
        }
        try {
            if (tokenService.isExpired(token)) {
                JSONObject responseBody = new JSONObject();
                responseBody.put("message", "Your authenticationToken has expired,please authenticate again.");
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
                return false;
            }
        } catch (Exception e) {
            JSONObject responseBody = new JSONObject();
            responseBody.put("message", " Error occurred when parsing the token:" + e.getMessage());
            responseBody.put("code", ExceptionCode.UNKNOWN_EX.code());
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
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            PrintWriter out = response.getWriter();
            out.print(responseBody.toJSONString());
            out.flush();
            return false;
        }
        try {
            if (tokenService.isTampered(token, userName)) {
                JSONObject responseBody = new JSONObject();
                responseBody.put("message", "Your userName is not consistent with that in token.");
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
                return false;
            }
        } catch (Exception e) {
            JSONObject responseBody = new JSONObject();
            responseBody.put("message", " Error occurred when parsing the token:" + e.getMessage());
            responseBody.put("code", ExceptionCode.UNKNOWN_EX.code());
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
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            PrintWriter out = response.getWriter();
            out.print(responseBody.toJSONString());
            out.flush();
            return false;
        }
        return true;
    }
}
