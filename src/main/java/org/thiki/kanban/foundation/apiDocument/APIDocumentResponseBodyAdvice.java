package org.thiki.kanban.foundation.apiDocument;

/**
 * Created by xubt on 9/14/16.
 */

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Order(1)
@ControllerAdvice(basePackages = "org.thiki")
public class APIDocumentResponseBodyAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        APIDocument.response = o;
        APIDocument.url = serverHttpRequest.getURI();
        APIDocument.endRequest();
        return o;
    }
}
