package org.thiki.kanban.foundation.config;

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
 * Created by xubt on 8/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RequestBodyFilterTest extends TestBase {
    @Scenario("当客户端发起POST请求但未发送请求体时,请求将被拦截并告知客户端相应错误")
    @Test
    public void NotAllowedIfRequestBodyIsNotProvide() {
        given().contentType(ContentType.JSON)
                .post("/passwordRetrieval")
                .then()
                .statusCode(ExceptionCode.INVALID_PARAMS.code())
                .body("code", equalTo(ExceptionCode.INVALID_PARAMS.code()))
                .body("message", equalTo("You specified http method is post,but payload is not provided."));
    }

    @Scenario("当客户端发起POST请求但请求体为空时,请求将被拦截并告知客户端相应错误")
    @Test
    public void NotAllowedIfRequestBodyIsEmpty() {
        given().body("{}")
                .contentType(ContentType.JSON)
                .post("/passwordRetrieval")
                .then()
                .statusCode(ExceptionCode.INVALID_PARAMS.code())
                .body("code", equalTo(ExceptionCode.INVALID_PARAMS.code()))
                .body("message", equalTo("You specified http method is post,but payload is not provided."));
    }
}
