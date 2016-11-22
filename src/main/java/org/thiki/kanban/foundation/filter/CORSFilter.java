package org.thiki.kanban.foundation.filter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by xubt on 7/26/16.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {

    public static final String REQUEST_REFERER_NAME = "referer";

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        response.setHeader("Access-Control-Allow-Origin", getRemoteHost(request));
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,token,userName,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
            chain.doFilter(req, res);
        }
    }

    private String getRemoteHost(HttpServletRequest request) {
        URI referURI;
        try {
            String referer = request.getHeader(REQUEST_REFERER_NAME);
            if (referer == null) {
                return null;
            }
            referURI = new URI(request.getHeader(REQUEST_REFERER_NAME));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return "http://" + referURI.getHost() + ":" + referURI.getPort();
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }
}
