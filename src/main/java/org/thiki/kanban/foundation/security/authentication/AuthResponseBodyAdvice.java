package org.thiki.kanban.foundation.security.authentication;

/**
 * Created by xubt on 9/14/16.
 */

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

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
        if (responseBody instanceof Map) {
            Object status = ((Map) responseBody).get("status");
            if (status == null) {
                JSONObject jsonObjectResponse = (JSONObject) responseBody;
                Map<String, Object> links = (Map<String, Object>) jsonObjectResponse.get("_links");
                String userName = String.valueOf(serverHttpRequest.getHeaders().get("userName"));

                ResourceLinks resourceLinks = new ResourceLinks(links, userName);
                JSONObject authenticatedLinks = resourceLinks.auth();
                System.out.println(authenticatedLinks.toJSONString());
                jsonObjectResponse.put("_links", authenticatedLinks);
                return jsonObjectResponse;
            }
        }
        return responseBody;
    }
}
