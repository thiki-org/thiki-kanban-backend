package org.thiki.kanban.foundation.common;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.InputStream;

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

    public static HttpEntity build(UrlResource urlResource) throws IOException {
        InputStream in = urlResource.getInputStream();
        return Response.build(StringUtils.newStringUtf8(Base64.encodeBase64(IOUtils.toByteArray(in), false)));
    }

    public static HttpEntity build(String s) {
        return ResponseEntity
                .ok().contentType(MediaType.TEXT_PLAIN)
                .body(s);
    }
}
