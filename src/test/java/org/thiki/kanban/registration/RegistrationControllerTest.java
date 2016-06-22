package org.thiki.kanban.registration;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thiki.kanban.TestBase;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

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
    }
}
