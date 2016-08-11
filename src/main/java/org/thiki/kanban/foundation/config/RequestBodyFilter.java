package org.thiki.kanban.foundation.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thiki.kanban.foundation.exception.ExceptionCode;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by xubt on 7/10/16.
 */
@Order(0)
public class RequestBodyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String httpMethod = ((RequestFacade) servletRequest).getMethod();

        HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper((HttpServletRequest) servletRequest);
        InputStream inputStream = requestWrapper.getInputStream();
        StringBuilder bodyStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        if (inputStream != null) {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                bodyStringBuilder.append(charBuffer, 0, bytesRead);
            }
        }
        if (RequestMethod.POST.name().equals(httpMethod) && ("".equals(bodyStringBuilder.toString()) || "{}".equals(bodyStringBuilder.toString()))) {
            writeResponse(servletResponse, "You specified http method is post,but payload is not provided.", ExceptionCode.INVALID_PARAMS.code(), ExceptionCode.INVALID_PARAMS.code());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private void writeResponse(ServletResponse servletResponse, String message, int code, int httpCode) throws IOException {
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", message);
        responseBody.put("code", code);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json");
        response.setStatus(httpCode);
        PrintWriter out = response.getWriter();
        out.print(responseBody.toJSONString());
        out.flush();
    }
}
