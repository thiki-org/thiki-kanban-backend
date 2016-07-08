package org.thiki.kanban.login;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.common.FileUtil;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by xubt on 7/5/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllerTest extends TestBase {
    @Test
    public void askForAuthenticationWhenUserIsExists() {
        String publicKey = FileUtil.readFile(publicKeyFilePath);
        given().header("name", "foo")
                .when()
                .get("/identification")
                .then()
                .statusCode(200)
                .body("publicKey", equalTo(publicKey));
    }
}
