package org.thiki.kanban.entrance.auth;

/**
 * Created by xubt on 24/10/2016.
 */
public enum MethodTye {
    GET("get"),
    POST("post"),
    PUT("put"),
    DELETE("delete");

    private String methodType;

    MethodTye(String methodType) {
        this.methodType = methodType;
    }
}
