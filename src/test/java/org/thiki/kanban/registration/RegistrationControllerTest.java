package org.thiki.kanban.registration;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;
import org.thiki.kanban.foundation.exception.ExceptionCode;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;

/**
 * Created by xubt on 6/16/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationControllerTest extends TestBase {

    @Test
    public void registerNewUser_shouldReturn201WhenRegisterSuccessfully() {
        given().header("token", "aaaa")
                .body("{\"name\":\"zhangsan\",\"email\":\"zhangsan@gmail.com\"," +
                        " \"phone\":\"19234448953\", \"password\":\"JKGHF*J$%\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/registration")
                .then()
                .statusCode(201)
                .body("userProfile.phone", equalTo("19234448953"))
                .body("userProfile.name", equalTo("zhangsan"))
                .body("userProfile.id", equalTo("fooId"))
                .body("userProfile.email", equalTo("zhangsan@gmail.com"))
                .body("userRegistration.password", equalTo("JKGHF*J$%"))
                .body("userRegistration.recoveryPhone", equalTo("19234448953"))
                .body("userRegistration.id", equalTo("fooId"))
                .body("userRegistration.recoveryEmail", equalTo("zhangsan@gmail.com"))
                .body("userRegistration.userId", equalTo("fooId"))
                .body("userRegistration.status", equalTo(0))
                .body("_links.self.href", equalTo("http://localhost:8007/registration"));

        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_user_profile").size());
        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_user_registration").size());
    }
    
    @Test
    public void registerNewUser_shouldRejectWithConflictWhenUserNameExists(){

        jdbcTemplate.execute("INSERT INTO  kb_user_profile (id,email,name,delete_status) " +
                "VALUES ('fooUserId','foo@bar.com','zhangsan',0)");

        given().header("token", "aaaa")
                .body("{\"name\":\"zhangsan\",\"email\":\"zhangsan@gmail.com\"," +
                        " \"phone\":\"19234448953\", \"password\":\"JKGHF*J$%\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/registration")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("code", equalTo(ExceptionCode.USER_EXISTS.code()))
                .body("message", equalTo("用户名zhangsan已存在"))
//                .body("_links.self.href", equalTo("http://localhost:8007/registration"))
        ;

        assertEquals(1, jdbcTemplate.queryForList("SELECT * FROM kb_user_profile").size());
        assertEquals(0, jdbcTemplate.queryForList("SELECT * FROM kb_user_registration").size());
    }
}
