package org.thiki.kanban.passwordRetrieval;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.exception.ExceptionCode;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by xubt on 8/8/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PasswordRetrievalControllerTest extends TestBase {

    @Scenario("当用户请求找回密码时,需要提供邮箱,如果未提供则告知客户端错误")
    @Test
    public void NotAllowedIfEmailIsNotProvide() {
        given().header("userId", "11222")
                .body("{\"noEmail\":\"noEmail\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/passwordRetrieval")
                .then()
                .statusCode(ExceptionCode.INVALID_PARAMS.code())
                .body("code", equalTo(ExceptionCode.INVALID_PARAMS.code()))
                .body("message", equalTo("用于找回密码的邮箱不能为空."));
    }
}
