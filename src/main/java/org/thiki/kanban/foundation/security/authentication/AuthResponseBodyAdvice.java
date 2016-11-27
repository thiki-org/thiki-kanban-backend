package org.thiki.kanban.foundation.security.authentication;

/**
 * Created by xubt on 9/14/16.
 */

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Map;

@Order(0)
@ControllerAdvice(basePackages = "org.thiki")
public class AuthResponseBodyAdvice implements ResponseBodyAdvice {
    private static Logger logger = LoggerFactory.getLogger(AuthResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object responseBody, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (responseBody instanceof Map) {
            Object status = ((Map) responseBody).get("status");
            if (status == null) {
                if (responseBody instanceof Map) {
                    return rebuildEntityLinks((JSONObject) responseBody, serverHttpRequest);
                }
            }
        }
        return responseBody;
    }

    private Object rebuildEntityLinks(JSONObject mainResourceBody, ServerHttpRequest serverHttpRequest) {
        logger.info("rebuilding the links of the response body :[%s]", mainResourceBody);
        List userNameObject = serverHttpRequest.getHeaders().get("userName");
        String userName = "";
        if (userNameObject != null) {
            userName = String.valueOf(userNameObject.get(0));
        }
        for (Object resourceFieldValue : mainResourceBody.values()) {
            if (resourceFieldValue instanceof List) {
                List resourceFieldValueList = (List) resourceFieldValue;
                for (Object childResourceObject : resourceFieldValueList) {
                    if (childResourceObject instanceof JSONObject) {
                        buildLinks((JSONObject) childResourceObject, userName);
                    }
                }
            }
        }
        Object buildResult = buildLinks(mainResourceBody, userName);
        logger.info("rebuild result:%s", buildResult);
        return buildResult;
    }

    private Object buildLinks(JSONObject jsonObjectResponse, String userName) {
        Map<String, Object> links = (Map<String, Object>) jsonObjectResponse.get("_links");
        ResourceLinks resourceLinks = new ResourceLinks(links, userName);
        JSONObject authenticatedLinks = resourceLinks.auth();
        jsonObjectResponse.put("_links", authenticatedLinks);
        return jsonObjectResponse;
    }
}
