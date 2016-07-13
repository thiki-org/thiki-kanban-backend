package org.thiki.kanban.publickey;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.common.FileUtil;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by xubt on 7/13/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PublicKeyControllerTest extends TestBase {
    @Scenario("当用户请求登录时,首先需要向系统发送一次认证请求,系统确认该用户合法时,将公钥发送至客户端")
    @Test
    public void identification_askForAuthenticationWhenUserIsExists() {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password,salt) " +
                "VALUES ('fooUserId','someone@gmail.com','someone','148412d9df986f739038ad22c77459f2','fooId')");
        String publicKey = FileUtil.readFile(publicKeyFilePath);
        given().header("name", "someone")
                .when()
                .get("/public-key")
                .then()
                .statusCode(200)
                .body("publicKey", equalTo(publicKey))
                .body("_links.login.href", equalTo("http://localhost:8007/login?identity=someone&password=yourPassWord"))
                .body("_links.registration.href", equalTo("http://localhost:8007/registration"));

    }

    @Scenario("当用户请求登录时,首先需要向系统发送一次认证请求,如果待认证的用户不存在,告知客户端参数错误")
    @Test
    public void identification_shouldThrowInvalidParamsExceptionWhenUserIsNotExists() {
        given().header("name", "foo")
                .when()
                .get("/public-key")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("No user named foo is found."));
    }
}
