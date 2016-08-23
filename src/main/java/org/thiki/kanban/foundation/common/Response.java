package org.thiki.kanban.foundation.common;

import com.alibaba.fastjson.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by xubitao on 1/2/16.
 */
public class Response {
    public static HttpEntity build(RestResource restResource) {
        JSONArray resources = restResource.getResources();
        ResponseEntity responseEntity = new ResponseEntity(resources != null ? resources : restResource.getResource(), HttpStatus.OK);
        return responseEntity;
    }

    public static HttpEntity post(RestResource restResource) {
        JSONArray resources = restResource.getResources();
        ResponseEntity responseEntity = new ResponseEntity(resources != null ? resources : restResource.getResource(), HttpStatus.CREATED);
        return responseEntity;
    }

    public static HttpEntity unauthorised(RestResource restResource) {
        JSONArray resources = restResource.getResources();
        ResponseEntity responseEntity = new ResponseEntity(resources != null ? resources : restResource.getResource(), HttpStatus.UNAUTHORIZED);
        return responseEntity;
    }

    public static HttpEntity error(RestResource restResource) {
        JSONArray resources = restResource.getResources();
        ResponseEntity responseEntity = new ResponseEntity(resources != null ? resources : restResource.getResource(), HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
}
