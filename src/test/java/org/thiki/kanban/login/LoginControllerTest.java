package org.thiki.kanban.login;

import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.security.rsa.RSAService;
import org.thiki.kanban.foundation.security.token.TokenService;

import javax.annotation.Resource;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Created by xubt on 7/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllerTest extends TestBase {

    @Resource
    private RSAService rsaService;
    @Resource
    private TokenService tokenService;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        rsaService = spy(rsaService);
        ReflectionTestUtils.setField(tokenService, "rsaService", rsaService);
        when(rsaService.encryptWithDefaultKey(any(String.class))).thenReturn("4988ca54a84321490e03fd906b5d2425afba80914c282271fd83ad1438ec8b55976cf77197a77b08c750bfb5e6173790f9f95f4e07a4f273d6fad3645e8377ed8ea865770a8aa4ff05168a98dc2417a4254405fb1639871cfc63f0dd5871a4805dc3778c106d37010b2c20adedd0117a2a8e63632744fa4e33151d880eed022e");
    }

    @Scenario("用户携带通过公钥加密的密码登录系统时,系统通过私钥对其解密,解密后再通过MD5加密与数据库现有系统匹配,如果匹配通过则颁发token")
    @Test
    public void login_loginSuccessfully() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password,salt) " +
                "VALUES ('fooUserId','someone@gmail.com','someone','148412d9df986f739038ad22c77459f2','fooId')");
        String publicKey = rsaService.loadKey(publicKeyFilePath);
        String password = "foo";
        String rsaPassword = rsaService.encrypt(publicKey, password);
        given().param("identity", "someone")
                .param("password", rsaPassword)
                .contentType(ContentType.JSON)
                .when()
                .get("/login")
                .then()
                .statusCode(200)
                .body("token", equalTo("4988ca54a84321490e03fd906b5d2425afba80914c282271fd83ad1438ec8b55976cf77197a77b08c750bfb5e6173790f9f95f4e07a4f273d6fad3645e8377ed8ea865770a8aa4ff05168a98dc2417a4254405fb1639871cfc63f0dd5871a4805dc3778c106d37010b2c20adedd0117a2a8e63632744fa4e33151d880eed022e"));
    }

    @Scenario("用户携带通过公钥加密的密码登录系统时,系统通过私钥对其解密,解密后再通过MD5加密与数据库现有系统匹配,如果匹配未通过则登录失败")
    @Test
    public void login_shouldLoginFailedIfUserNameOrPasswordIsIncorrect() throws Exception {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password,salt) " +
                "VALUES ('fooUserId','someone@gmail.com','someone','148412d9df986f739038ad22c77459f2','fooId')");
        String publicKey = rsaService.loadKey(publicKeyFilePath);
        String password = "wrongPassword";
        String rsaPassword = rsaService.encrypt(publicKey, password);
        given().param("identity", "someone")
                .param("password", rsaPassword)
                .contentType(ContentType.JSON)
                .when()
                .get("/login")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Your username or password is incorrect."));
    }

    @Scenario("用户携带通过公钥加密的密码登录系统时,系统通过私钥对其解密,解密后再通过MD5加密与数据库现有系统匹配,如果匹配通过则颁发token")
    @Test
    public void login_loginFailed() throws Exception {
        given().param("password", "")
                .when()
                .get("/login")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("Identity is required."));
    }
}
