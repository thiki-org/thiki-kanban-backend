package org.thiki.kanban.foundation.security.authentication;

/**
 * Created by xubt on 24/10/2016.
 */
public enum MethodType {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete");

    private String methodType;

    MethodType(String methodType) {
        this.methodType = methodType;
    }
}
