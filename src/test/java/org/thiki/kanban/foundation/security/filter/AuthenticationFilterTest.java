package org.thiki.kanban.foundation.security.filter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.AuthenticationTestBase;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.foundation.security.token.AuthenticationToken;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by xubt on 7/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthenticationFilterTest extends AuthenticationTestBase {
    @Resource
    public RSAService rsaService;

    @Scenario("当请求需要认证时,如果没有携带token,则告知客户端需要授权")
    @Test
    public void shouldReturn401WhenAuthIsRequired() throws Exception {
        given().when()
                .get("/resource")
                .then()
                .statusCode(401)
                .body("code", equalTo(401))
                .body("message", equalTo("AuthenticationToken is required,please authenticate first."))
                .body("_links.identification.href", equalTo("/identification"));
    }

    @Scenario("如果用户在5分钟内未发送请求,token将会失效,告知客户端需要重新授权")
    @Test
    public void shouldReturnTimeOut() throws Exception {
        String userName = "foo";
        String expiredToken = buildExpiredToken(userName, new Date(), -5);
        given().header("token", expiredToken)
                .when()
                .get("/resource")
                .then()
                .statusCode(401)
                .body("code", equalTo(401))
                .body("message", equalTo("Your authenticationToken has expired,please authenticate again."))
                .body("_links.identification.href", equalTo("/identification"));
    }


    @Scenario("当token不为空且未失效时,请求到达后更新token的有效期")
    @Test
    public void shouldUpdateTokenExpiredTime() {

    }

    @Scenario("当token中的用户名与header中携带的用户名不一致时,告知客户端认证未通过")
    @Test
    public void shouldAuthenticatedFailedWhenUserNameIsNotConsistent() throws Exception {
        String userName = "foo";
        String tamperedUserName = "fee";
        String expiredToken = buildExpiredToken(userName, new Date(), 2);
        given().header("userName", tamperedUserName)
                .header("token", expiredToken)
                .when()
                .get("/resource")
                .then()
                .statusCode(401)
                .body("code", equalTo(401))
                .body("message", equalTo("Your userName is not consistent with that in token."))
                .body("_links.identification.href", equalTo("/identification"));
    }

    private String buildExpiredToken(String userName, Date date, int minute) throws Exception {
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setUserName(userName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MINUTE, minute);

        Date expirationTime = rightNow.getTime();
        String expirationTimeStr = sdf.format(expirationTime);
        authenticationToken.setExpirationTime(expirationTimeStr);
        String encryptedToken = rsaService.encryptWithDefaultKey(authenticationToken.toString());

        return encryptedToken;
    }
}
