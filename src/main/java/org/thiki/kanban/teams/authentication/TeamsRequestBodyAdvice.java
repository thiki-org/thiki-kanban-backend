package org.thiki.kanban.teams.authentication;

/**
 * Created by xubt on 9/14/16.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

@Order(1)
@ControllerAdvice(basePackages = "org.thiki")
public class TeamsRequestBodyAdvice implements RequestBodyAdvice {

    @Autowired(required = true)
    private HttpServletRequest request;

    @Autowired
    private ApplicationContextProvider applicationContextProvider;

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return null;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        return httpInputMessage;
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        if (request.getHeader("authentication").equals("no")) {
            return o;
        }
        String currentURL = request.getRequestURI();
        Map<String, Authentication> authProvidersauthProviders = ApplicationContextProvider.getApplicationContext().getBeansOfType(Authentication.class);
        for (Map.Entry entry : authProvidersauthProviders.entrySet()) {
            Authentication auth = (Authentication) entry.getValue();
            auth.authenticate(currentURL, request.getHeader("userName"));
        }
        return o;
    }
}
