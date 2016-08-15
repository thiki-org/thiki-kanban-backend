package org.thiki.kanban.passwordRetrieval;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.annotations.Scenario;
import org.thiki.kanban.foundation.common.VerificationCodeService;
import org.thiki.kanban.foundation.exception.ExceptionCode;

import javax.annotation.Resource;

import static com.jayway.restassured.RestAssured.given;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by xubt on 8/8/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PasswordRetrievalControllerTest extends TestBase {
    @Resource
    private PasswordRetrievalService passwordRetrievalService;


    @Scenario("当用户请求找回密码时,需要提供邮箱,如果未提供则告知客户端错误")
    @Test
    public void NotAllowedIfEmailIsNotProvide() {
        given().body("{\"noEmail\":\"noEmail\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/passwordRetrievalApplication")
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
                .post("/passwordRetrievalApplication")
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
                .post("/passwordRetrievalApplication")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("code", equalTo(PasswordRetrievalCodes.EMAIL_IS_NOT_EXISTS.code()))
                .body("message", equalTo(PasswordRetrievalCodes.EMAIL_IS_NOT_EXISTS.message()));
    }

    @Scenario("邮箱通过格式校验且存在后，发送找回密码的验证码到邮箱")
    @Test
    public void sendVerificationCode() {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('fooUserId','766191920@qq.com','徐涛','password')");
        given().body("{\"email\":\"766191920@qq.com\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/passwordRetrievalApplication")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("_links.passwordResetApplication.href", equalTo("http://localhost:8007/passwordResetApplication"));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_password_retrieval where email='766191920@qq.com'").size());
    }

    @Scenario("用户取得验证码后，和邮箱一起发送到服务端验证，如果验证码正确且未过期，则发送密码重置的链接")
    @Test
    public void verifyVerificationCode() {
        jdbcTemplate.execute("INSERT INTO  kb_user_registration (id,email,name,password) " +
                "VALUES ('fooUserId','766191920@qq.com','徐涛','password')");
        jdbcTemplate.execute("INSERT INTO  kb_password_retrieval (id,email,verification_code) " +
                "VALUES ('fooUserId','766191920@qq.com','000000')");

        VerificationCodeService verificationCodeService = mock(VerificationCodeService.class);
        when(verificationCodeService.generate()).thenReturn("000000");
        ReflectionTestUtils.setField(passwordRetrievalService, "verificationCodeService", verificationCodeService);

        given().body("{\"email\":\"766191920@qq.com\",\"verificationCode\":\"000000\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/passwordResetApplication")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("_links.password.href", equalTo("http://localhost:8007/password"));
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_password_retrieval where email='766191920@qq.com'").size());
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_password_reset where email='766191920@qq.com'").size());
    }
}
