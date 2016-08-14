package org.thiki.kanban.passwordRetrieval;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.exception.ExceptionCode;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by xubt on 8/8/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PasswordRetrievalControllerTest extends TestBase {

    @Scenario("当用户请求找回密码时,需要提供邮箱,如果未提供则告知客户端错误")
    @Test
    public void NotAllowedIfEmailIsNotProvide() {
        given().body("{\"noEmail\":\"noEmail\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/passwordRetrievalApply")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code", equalTo(ExceptionCode.INVALID_PARAMS.code()))
                .body("message", equalTo("用于找回密码的邮箱不能为空."));
    }

    @Scenario("当用户请求找回密码时,需要提供邮箱,如果邮箱格式错误则告知客户端错误")
    @Test
    public void NotAllowedIfEmailFormatIsNotCorrect() {
        given().body("{\"email\":\"email\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/passwordRetrievalApply")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code", equalTo(ExceptionCode.INVALID_PARAMS.code()))
                .body("message", equalTo("邮箱格式错误."));
    }

    @Scenario("当用户请求找回密码时,需要提供邮箱,如果邮箱不存在则告知客户端错误")
    @Test
    public void NotAllowedIfEmailFormatIsNotExists() {
        given().body("{\"email\":\"email@email.com\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/passwordRetrievalApply")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code", equalTo(PasswordRetrievalCodes.EmailIsNotExists.code()))
                .body("message", equalTo(PasswordRetrievalCodes.EmailIsNotExists.message()));
    }

    @Scenario("邮箱通过格式校验且存在后，发送找回密码的验证码到邮箱")
    @Test
    public void sendVerificationCode() {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('fooUserId','766191920@qq.com','徐涛','password')");
        given().body("{\"email\":\"766191920@qq.com\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/passwordRetrievalApply")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("_links.passwordRetrieval.href", equalTo("http://localhost:8007/passwordRetrieval"));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_password_retrieval where email='766191920@qq.com'").size());
    }
}
