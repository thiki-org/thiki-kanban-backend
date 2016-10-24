package org.thiki.kanban.board.authentication;

import org.apache.catalina.connector.RequestFacade;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.thiki.kanban.teams.authentication.ApplicationContextProvider;
import org.thiki.kanban.teams.authentication.Authentication;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;

/**
 * Created by xubt on 10/24/16.
 */
@Service
@ConfigurationProperties(prefix = "security")
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String currentURL = ((RequestFacade) servletRequest).getRequestURI();
        String method = ((RequestFacade) servletRequest).getMethod();
        Map<String, Authentication> authProviders = ApplicationContextProvider.getApplicationContext().getBeansOfType(Authentication.class);
        for (Map.Entry entry : authProviders.entrySet()) {
            Authentication auth = (Authentication) entry.getValue();
            if (auth.matchPath(currentURL)) {
                auth.authenticate(currentURL, method, ((RequestFacade) servletRequest).getHeader("userName"));
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
