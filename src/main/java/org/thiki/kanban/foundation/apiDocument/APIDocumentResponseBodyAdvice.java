package org.thiki.kanban.foundation.apiDocument;

/**
 * Created by xubt on 9/14/16.
 */

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@Order(1)
@ControllerAdvice(basePackages = "org.thiki")
public class APIDocumentResponseBodyAdvice implements ResponseBodyAdvice {

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
            if (status != null && ((int) status) > 300) {
                String localAddress;
                localAddress = "http://" + serverHttpRequest.getLocalAddress().getHostName() + ":" + serverHttpRequest.getLocalAddress().getPort();
                String url = localAddress + ((Map) responseBody).get("path");
                String queryString = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getQueryString();
                if (queryString != null) {
                    url += "?" + queryString;
                }
                APIDocument.url = url;
            }
        }
        APIDocument.endRequest();
        return responseBody;
    }
}
