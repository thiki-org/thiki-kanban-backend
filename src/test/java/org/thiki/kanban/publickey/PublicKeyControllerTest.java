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
    @Scenario("当用户请求登录或注册时,首先需要向系统发送一次认证请求,将公钥发送至客户端")
    @Test
    public void identification_askForAuthenticationWhenUserIsExists() {
        String publicKey = FileUtil.readFile(publicKeyFilePath);
        given().when()
                .get("/publicKey")
                .then()
                .statusCode(200)
                .body("publicKey", equalTo(publicKey))
                .body("_links.login.href", equalTo("http://localhost:8007/login?identity=yourIdentity&password=yourPassWord"))
                .body("_links.registration.href", equalTo("http://localhost:8007/registration"));
    }
}
