package org.thiki.kanban.foundation.aspect;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.security.Constants;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by xubt on 8/28/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ValidateAspectTest extends TestBase {
    @Scenario("当头部信息的userName和路径中的不一致时,告知客户端错误")
    @Test
    public void throwExceptionIfUserNameInHeaderIsNotEqualWithItInPath() throws Exception {
        given().header("userName", "someone")
                .body("{\"name\":\"teamName\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/thief/teams")
                .then()
                .statusCode(400)
                .body("message", equalTo(Constants.SECURITY_USERNAME_IN_HEADER_IS_NOT_CONSISTENT_WITH_PATH));
    }
}
