package org.thiki.kanban.foundation.security.authentication;

/**
 * Created by xubt on 24/10/2016.
 */
public enum MethodType {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");

    private String methodType;

    MethodType(String methodType) {
        this.methodType = methodType;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean equal(String method) {
        return methodType.equals(method);
    }
}
