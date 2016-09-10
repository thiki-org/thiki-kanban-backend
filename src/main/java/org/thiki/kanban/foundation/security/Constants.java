package org.thiki.kanban.foundation.security;

/**
 * Created by xubt on 7/10/16.
 */
public class Constants {
    public static final String SECURITY_IDENTIFY_PASSED = "passed";
    public static final String SECURITY_IDENTITY_NO_AUTHENTICATION_TOKEN = "当前用户未认证,请先登录认证。";
    public static final String SECURITY_IDENTITY_AUTHENTICATION_TOKEN_HAS_EXPIRE = "认证已经过期,请重新认证获取token.";
    public static final String SECURITY_IDENTITY_USER_NAME_IS_NOT_CONSISTENT = "请求头部的用户名与token中的不一致,请求可能被篡改。";
    public static final String SECURITY_USERNAME_IN_HEADER_IS_NOT_CONSISTENT_WITH_PATH = "请求头部的用户名与URL中的不一致,请求可能被篡改。";
    public static final String LOCAL_ADDRESS = "127.0.0.1";
    public static final String FREE_AUTHENTICATION = "no";
    public static final String HEADER_PARAMS_TOKEN = "token";
    public static final String HEADER_PARAMS_USER_NAME = "userName";
    public static final String HEADER_PARAMS_AUTHENTICATION = "authentication";
    public static final int TOKEN_EXPIRED_TIME = 20;
    public static final int SECURITY_IDENTITY_NO_AUTHENTICATION_TOKEN_CODE = 1101;
    public static final int SECURITY_IDENTITY_AUTHENTICATION_TOKEN_HAS_EXPIRE_CODE = 1102;
    public static final int SECURITY_IDENTITY_USER_NAME_IS_NOT_CONSISTENT_CODE = 1103;
    public static final int SECURITY_IDENTIFY_PASSED_CODE = 1100;
    public static final int SECURITY_IDENTIFY_UN_KNOW = 9999;
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
}
