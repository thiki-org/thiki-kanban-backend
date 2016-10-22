package org.thiki.kanban.teams.authentication;

/**
 * Created by xubt on 9/14/16.
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.thiki.kanban.foundation.apiDocument.APIDocument;

import java.util.Map;

@Order(0)
@ControllerAdvice(basePackages = "org.thiki")
public class AuthResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object responseBody, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        APIDocument.response = responseBody;
        APIDocument.url = serverHttpRequest.getURI().toString();
        if (responseBody instanceof Map) {
            Object status = ((Map) responseBody).get("status");
            if (status == null) {
                JSONObject jsonObjectResponse = (JSONObject) responseBody;
                Map<String, String> links = (Map<String, String>) jsonObjectResponse.get("_links");
                String userName = ((RequestFacade) serverHttpRequest).getHeader("userName");

                Tlinks tlinks = new Tlinks(links, userName);
            }
        }
        APIDocument.endRequest();
        return responseBody;
    }
}
